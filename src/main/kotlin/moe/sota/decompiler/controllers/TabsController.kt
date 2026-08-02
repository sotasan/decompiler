package moe.sota.decompiler.controllers

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ContainerEvent
import java.awt.event.ContainerListener
import javax.swing.ImageIcon
import moe.sota.decompiler.menus.file.FileCloseTab
import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.services.PreferenceService
import moe.sota.decompiler.transformers.Transformer
import moe.sota.decompiler.types.ClassType
import moe.sota.decompiler.views.TabView
import moe.sota.decompiler.views.TabsView
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TabsController(
    private val tabsView: TabsView,
    private val createTabController: (FileModel) -> TabController,
) : ActionListener, ContainerListener, KoinComponent {
    private val fileCloseTab: FileCloseTab by inject()

    val transformer: Transformer?
        get() = tabsView.comboBox.selectedItem as Transformer?

    private val controllers: List<TabController>
        get() =
            (0..<tabsView.tabCount).mapNotNull {
                (tabsView.getComponentAt(it) as TabView).tabController
            }

    init {
        tabsView.comboBox.addActionListener(this)
        tabsView.addContainerListener(this)
        PreferenceService.preferences.get("transformer", null)?.let {
            tabsView.comboBox.selectedItem = Transformer.valueOf(it)
        }
    }

    override fun actionPerformed(event: ActionEvent?) {
        PreferenceService.preferences.put("transformer", transformer?.name)
        controllers.filter { it.fileModel.type is ClassType }.forEach { it.update() }
    }

    override fun componentAdded(event: ContainerEvent) {
        fileCloseTab.isEnabled = tabsView.tabCount > 0
    }

    override fun componentRemoved(event: ContainerEvent) {
        (event.child as? TabView)?.tabController?.dispose()
        fileCloseTab.isEnabled = tabsView.tabCount > 0
    }

    fun addTab(fileModel: FileModel) {
        val existing = getController(fileModel)
        if (existing != null) {
            tabsView.selectedComponent = existing.tabView
            return
        }

        val controller = createTabController(fileModel)
        tabsView.addTab(fileModel.name, ImageIcon(fileModel.icon), controller.tabView)
        tabsView.selectedComponent = controller.tabView
        controller.update()
    }

    fun closeTab() {
        tabsView.removeTabAt(tabsView.selectedIndex)
    }

    fun clearTabs() {
        tabsView.removeAll()
    }

    private fun getController(fileModel: FileModel) = controllers.lastOrNull {
        it.fileModel === fileModel
    }
}
