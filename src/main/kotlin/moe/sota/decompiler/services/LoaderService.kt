package moe.sota.decompiler.services

import java.awt.Taskbar
import java.io.File
import java.util.jar.JarFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
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
import org.koin.core.component.get

object LoaderService : KoinComponent {
    private val scope = MainScope()

    fun load(file: File) {
        scope.launch {
            val tabsController = get<TabsController>()
            val treeController = get<TreeController>()
            val windowController = get<WindowController>()
            val windowView = get<WindowView>()

            setProgressState(windowView, Taskbar.State.INDETERMINATE)

            try {
                val archive =
                    withContext(Dispatchers.IO) {
                        val jar = JarFile(file)
                        val entries = jar.entries()
                        val archive = ArchiveModel(file.name)

                        while (entries.hasMoreElements()) {
                            val entry = entries.nextElement()
                            val packageModel = getChildByPath(archive, entry.name)
                            if (entry.isDirectory)
                                packageModel.children.add(PackageModel(entry.name))
                            else packageModel.children.add(FileModel(jar, entry))
                        }

                        archive
                    }

                windowController.activate()
                tabsController.clearTabs()
                treeController.setArchive(archive)
            } catch (e: Exception) {
                e.printStackTrace(System.err)
            } finally {
                setProgressState(windowView, Taskbar.State.OFF)
            }
        }
    }

    private fun setProgressState(windowView: WindowView, state: Taskbar.State) {
        if (
            Taskbar.isTaskbarSupported() &&
                Taskbar.getTaskbar().isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW)
        )
            Taskbar.getTaskbar().setWindowProgressState(windowView, state)
    }

    private fun getChildByPath(baseModel: BaseModel, path: String): BaseModel {
        for (child in baseModel.children) if (child is PackageModel && path.startsWith(child.path))
            return getChildByPath(child, path)

        return baseModel
    }
}
