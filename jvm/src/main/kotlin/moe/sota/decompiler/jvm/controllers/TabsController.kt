package moe.sota.decompiler.jvm.controllers

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.ImageIcon
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import moe.sota.decompiler.jvm.menus.file.FileCloseTab
import moe.sota.decompiler.jvm.models.FileModel
import moe.sota.decompiler.jvm.services.PreferenceService
import moe.sota.decompiler.jvm.transformers.Transformer
import moe.sota.decompiler.jvm.types.ClassType
import moe.sota.decompiler.jvm.views.TabView
import moe.sota.decompiler.jvm.views.TabsView
import org.koin.java.KoinJavaComponent.get

class TabsController(
    private val tabsView: TabsView,
    private val createTabController: (FileModel) -> TabController,
) : ActionListener, ChangeListener {
    val transformer: Transformer?
        get() = tabsView.comboBox.selectedItem as Transformer?

    init {
        tabsView.comboBox.addActionListener(this)
        tabsView.getModel().addChangeListener(this)
        val transformer = PreferenceService.PREFERENCES.get("transformer", null)
        if (transformer != null) tabsView.comboBox.setSelectedItem(Transformer.valueOf(transformer))
    }

    override fun actionPerformed(event: ActionEvent?) {
        PreferenceService.PREFERENCES.put("transformer", this.transformer?.name)
        for (i in 0..<tabsView.tabCount) {
            val controller: TabController? = (tabsView.getComponentAt(i) as TabView).tabController
            if (controller?.fileModel?.type is ClassType) controller.updateAsync()
        }
    }

    override fun stateChanged(changeEvent: ChangeEvent?) {
        val fileCloseTab = get<FileCloseTab>(FileCloseTab::class.java)
        fileCloseTab.setEnabled(tabsView.tabCount > 0)
    }

    fun addTab(fileModel: FileModel) {
        var controller = getController(fileModel)
        if (controller == null) {
            controller = createTabController(fileModel)
            val icon = ImageIcon(fileModel.icon)
            val component = controller.tabView
            controller.updateAsync().thenRun {
                if (getController(fileModel) == null) {
                    tabsView.addTab(fileModel.getName(), icon, component)
                    tabsView.setSelectedComponent(component)
                }
            }
        } else {
            tabsView.setSelectedComponent(controller.tabView)
        }
    }

    fun closeTab() {
        tabsView.removeTabAt(tabsView.selectedIndex)
    }

    fun clearTabs() {
        tabsView.removeAll()
    }

    private fun getController(fileModel: FileModel?): TabController? {
        var controller: TabController? = null

        for (i in 0..<tabsView.tabCount) {
            val current: TabController? = (tabsView.getComponentAt(i) as TabView).tabController
            if (fileModel === current?.fileModel) controller = current
        }

        return controller
    }
}
