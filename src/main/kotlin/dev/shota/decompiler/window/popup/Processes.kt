package dev.shota.decompiler.window.popup

import com.sun.tools.attach.VirtualMachine
import com.sun.tools.attach.VirtualMachineDescriptor
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.VBox
import java.io.File
import java.lang.management.ManagementFactory

class Processes : Popup("file.openProcess") {

    init {
        val root = VBox()

        val table = TableView<VirtualMachineDescriptor>()
        root.children.add(table)
        for (vm in VirtualMachine.list())
            if (ManagementFactory.getRuntimeMXBean().pid != vm.id().toLong())
                table.items.add(vm)

        val id = TableColumn<VirtualMachineDescriptor, String>("ID")
        val entry = TableColumn<VirtualMachineDescriptor, String>("Entry")
        id.setCellValueFactory { SimpleStringProperty(it.value.id()) }
        entry.setCellValueFactory { SimpleStringProperty(it.value.displayName().split(" ").first()) }
        table.columns.addAll(id, entry)

        val button = Button("Open")
        button.setOnAction {
            val vm = VirtualMachine.attach(table.selectionModel.selectedItem)
            vm.loadAgent(File(javaClass.protectionDomain.codeSource.location.path).path)
            vm.detach()
        }
        root.children.add(button)

        run(root)
    }

}