package moe.sota.decompiler.controllers

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.ImageIcon
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TabsController(
    private val tabsView: TabsView,
    private val createTabController: (FileModel) -> TabController,
) : ActionListener, ChangeListener, KoinComponent {
    private val fileCloseTab: FileCloseTab by inject()
    private val scope = MainScope()

    val transformer: Transformer?
        get() = tabsView.comboBox.selectedItem as Transformer?

    private val controllers: List<TabController>
        get() =
            (0..<tabsView.tabCount).mapNotNull {
                (tabsView.getComponentAt(it) as TabView).tabController
            }

    init {
        tabsView.comboBox.addActionListener(this)
        tabsView.model.addChangeListener(this)
        PreferenceService.preferences.get("transformer", null)?.let {
            tabsView.comboBox.selectedItem = Transformer.valueOf(it)
        }
    }

    override fun actionPerformed(event: ActionEvent?) {
        PreferenceService.preferences.put("transformer", transformer?.name)
        controllers
            .filter { it.fileModel.type is ClassType }
            .forEach { scope.launch { it.update() } }
    }

    override fun stateChanged(changeEvent: ChangeEvent?) {
        fileCloseTab.isEnabled = tabsView.tabCount > 0
    }

    fun addTab(fileModel: FileModel) {
        val existing = getController(fileModel)
        if (existing != null) {
            tabsView.selectedComponent = existing.tabView
            return
        }

        val controller = createTabController(fileModel)
        val icon = ImageIcon(fileModel.icon)
        val component = controller.tabView
        scope.launch {
            controller.update()
            if (getController(fileModel) == null) {
                tabsView.addTab(fileModel.name, icon, component)
                tabsView.selectedComponent = component
            }
        }
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
