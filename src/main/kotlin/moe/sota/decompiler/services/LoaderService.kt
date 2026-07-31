package moe.sota.decompiler.services

import java.awt.Taskbar
import java.io.File
import java.util.jar.JarFile
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.sota.decompiler.controllers.TabsController
import moe.sota.decompiler.controllers.TreeController
import moe.sota.decompiler.controllers.WindowController
import moe.sota.decompiler.models.ArchiveModel
import moe.sota.decompiler.models.BaseModel
import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.models.PackageModel
import moe.sota.decompiler.views.WindowView
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object LoaderService : KoinComponent {
    private val scope = MainScope()

    private val tabsController: TabsController by inject()
    private val treeController: TreeController by inject()
    private val windowController: WindowController by inject()
    private val windowView: WindowView by inject()

    fun load(file: File) {
        scope.coroutineContext.cancelChildren()
        setProgressState(Taskbar.State.INDETERMINATE)

        scope.launch {
            try {
                val archive =
                    withContext(Dispatchers.IO) {
                        val jar = JarFile(file)

                        ArchiveModel(file.name).apply {
                            for (entry in jar.entries()) {
                                ensureActive()
                                val child =
                                    if (entry.isDirectory) PackageModel(entry.name)
                                    else FileModel(jar, entry)
                                getChildByPath(this, entry.name).children.add(child)
                            }
                        }
                    }

                windowController.activate()
                tabsController.clearTabs()
                treeController.setArchive(archive)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace(System.err)
            } finally {
                // A superseded load must leave the progress state to the load that replaced it
                if (isActive) setProgressState(Taskbar.State.OFF)
            }
        }
    }

    private fun setProgressState(state: Taskbar.State) {
        if (!Taskbar.isTaskbarSupported()) return

        val taskbar = Taskbar.getTaskbar()
        if (taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW))
            taskbar.setWindowProgressState(windowView, state)
    }

    private fun getChildByPath(baseModel: BaseModel, path: String): BaseModel =
        baseModel.children
            .firstOrNull { it is PackageModel && path.startsWith(it.path) }
            ?.let { getChildByPath(it, path) } ?: baseModel
}
