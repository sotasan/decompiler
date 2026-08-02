package moe.sota.decompiler.controllers

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.transformers.Transformer
import moe.sota.decompiler.types.ClassType
import moe.sota.decompiler.types.ImageType
import moe.sota.decompiler.views.TabView
import org.fife.ui.rsyntaxtextarea.SyntaxConstants

class TabController(
    val fileModel: FileModel,
    val tabView: TabView,
    private val tabsController: TabsController,
) {
    private val scope = MainScope()

    init {
        tabView.tabController = this
    }

    fun update() {
        scope.coroutineContext.cancelChildren()
        scope.launch {
            if (fileModel.type is ImageType) {
                tabView.showImage()
                return@launch
            }

            try {
                val transformer = tabsController.transformer
                tabView.textArea.text = withContext(Dispatchers.Default) { getText(transformer) }
                fileModel.type?.let { tabView.textArea.syntaxEditingStyle = it.syntax }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                tabView.textArea.text = e.stackTraceToString().trim()
                tabView.textArea.syntaxEditingStyle = SyntaxConstants.SYNTAX_STYLE_NONE
            }

            tabView.scrollPane.horizontalScrollBar.value = 0
            tabView.scrollPane.verticalScrollBar.value = 0
        }
    }

    fun dispose() = scope.cancel()

    private fun getText(transformer: Transformer?): String =
        if (fileModel.type is ClassType) transformer!!.newInstance().transform(fileModel)
        else fileModel.bytes.decodeToString()
}
