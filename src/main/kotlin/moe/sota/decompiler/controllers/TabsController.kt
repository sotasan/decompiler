package moe.sota.decompiler.controllers

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import moe.sota.decompiler.menus.file.FileCloseTab
import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.services.PreferenceService
import moe.sota.decompiler.transformers.Transformer
import moe.sota.decompiler.types.ClassType
import moe.sota.decompiler.views.TabView
import moe.sota.decompiler.views.TabsView
import org.koin.java.KoinJavaComponent.get

class TabsController(
    private val tabsView: TabsView,
    private val createTabController: (FileModel) -> TabController,
) : ActionListener, ChangeListener {
    private val scope = MainScope()

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
            if (controller?.fileModel?.type is ClassType) scope.launch { controller.update() }
        }
    }

    override fun stateChanged(changeEvent: ChangeEvent?) {
        val fileCloseTab = get<FileCloseTab>(FileCloseTab::class.java)
        fileCloseTab.setEnabled(tabsView.tabCount > 0)
    }

    fun addTab(fileModel: FileModel) {
        val existing = getController(fileModel)
        if (existing != null) {
            tabsView.setSelectedComponent(existing.tabView)
            return
        }

        val controller = createTabController(fileModel)
        val icon = fileModel.icon
        val component = controller.tabView
        scope.launch {
            controller.update()
            if (getController(fileModel) == null) {
                tabsView.addTab(fileModel.name, icon, component)
                tabsView.setSelectedComponent(component)
            }
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
