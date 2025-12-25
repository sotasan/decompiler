package moe.sota.decompiler.controllers

import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.types.ClassType
import moe.sota.decompiler.types.ImageType
import moe.sota.decompiler.views.TabView
import org.fife.ui.rsyntaxtextarea.SyntaxConstants
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.function.Consumer
import java.util.function.Function
import javax.swing.JScrollPane

class TabController(
    val fileModel: FileModel,
    val tabView: TabView,
    private val tabsController: TabsController
) {

    init {
        tabView.tabController = this
    }

    fun updateAsync(): CompletableFuture<Void?> {
        if (fileModel.type is ImageType) {
            val imageScrollPane = JScrollPane()
            tabView.setScrollPane(imageScrollPane)
            return CompletableFuture.completedFuture<Void?>(null)
        }

        return getTextAsync(fileModel)
            .thenAccept(Consumer { s: String? ->
                tabView.textArea.text = s
                val type = fileModel.type
                if (type != null)
                    tabView.textArea.setSyntaxEditingStyle(type.syntax)
            })
            .exceptionally(Function { e: Throwable? ->
                val stringWriter = StringWriter()
                val printWriter = PrintWriter(stringWriter)
                e?.printStackTrace(printWriter)
                tabView.textArea.text = stringWriter.toString().trim { it <= ' ' }
                tabView.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE)
                null
            })
            .thenRun {
                tabView.scrollPane.getHorizontalScrollBar().setValue(0)
                tabView.scrollPane.getVerticalScrollBar().setValue(0)
            }
    }

    private fun getTextAsync(fileModel: FileModel): CompletableFuture<String?> {
        return CompletableFuture.supplyAsync<String?> {
            try {
                return@supplyAsync if (fileModel.type is ClassType)
                    tabsController.transformer!!.newInstance().transform(fileModel)
                else String(fileModel.bytes, StandardCharsets.UTF_8)
            } catch (e: Exception) {
                throw CompletionException(e)
            }
        }
    }

}
