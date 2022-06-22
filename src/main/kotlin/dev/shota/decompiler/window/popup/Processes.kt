package dev.shota.decompiler.window.popup

import com.sun.tools.attach.VirtualMachine
import com.sun.tools.attach.VirtualMachineDescriptor
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.VBox

class Processes : Popup("file.openProcess") {

    init {
        val root = VBox()
        val table = TableView<VirtualMachineDescriptor>()
        val id = TableColumn<VirtualMachineDescriptor, String>("ID")
        val entry = TableColumn<VirtualMachineDescriptor, String>("Entry")
        id.setCellValueFactory { SimpleStringProperty(it.value.id()) }
        entry.setCellValueFactory { SimpleStringProperty(it.value.displayName().split(" ").first()) }
        table.columns.addAll(id, entry)
        root.children.add(table)

        for (vm in VirtualMachine.list())
            table.items.add(vm)

        val button = Button("Open")
        button.setOnAction {}
        root.children.add(button)

        run(root)
    }

}