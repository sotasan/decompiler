package moe.sota.decompiler.controllers

import java.io.PrintWriter
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import javax.swing.JScrollPane
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
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
    init {
        tabView.tabController = this
    }

    suspend fun update() {
        if (fileModel.type is ImageType) {
            val imageScrollPane = JScrollPane()
            tabView.setScrollPane(imageScrollPane)
            return
        }

        try {
            val transformer = tabsController.transformer
            val text = withContext(Dispatchers.Default) { getText(transformer) }
            tabView.textArea.text = text
            val type = fileModel.type
            if (type != null) tabView.textArea.setSyntaxEditingStyle(type.syntax)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            val stringWriter = StringWriter()
            e.printStackTrace(PrintWriter(stringWriter))
            tabView.textArea.text = stringWriter.toString().trim { it <= ' ' }
            tabView.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE)
        }

        tabView.scrollPane.getHorizontalScrollBar().setValue(0)
        tabView.scrollPane.getVerticalScrollBar().setValue(0)
    }

    private fun getText(transformer: Transformer?): String =
        if (fileModel.type is ClassType) transformer!!.newInstance().transform(fileModel)
        else String(fileModel.bytes, StandardCharsets.UTF_8)
}
