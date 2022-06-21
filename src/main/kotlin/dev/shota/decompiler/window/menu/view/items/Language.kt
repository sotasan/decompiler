package dev.shota.decompiler.window.menu.view.items

import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.ButtonGroup
import javax.swing.JMenu
import javax.swing.JRadioButtonMenuItem

object Language : JMenu() {

    private lateinit var bundle: ResourceBundle
    private val properties = mutableMapOf<String, Property<String>>()
    private val group = ButtonGroup()

    init {
        add(Translation(Locale.ENGLISH, true))
        add(Translation(Locale.GERMAN))

        val translation = get("view.language")
        text = translation.value
        translation.addListener { _, _, value -> text = value }
        mnemonic = KeyEvent.VK_L
    }

    fun get(key: String): Property<String> {
        val property = SimpleStringProperty(bundle.getString(key))
        properties[key] = property
        return property
    }

    private class Translation(val language: Locale, default: Boolean = false) : JRadioButtonMenuItem(language.getDisplayName(language), default), ActionListener {

        init {
            group.add(this)
            addActionListener(this)
            if (Locale.getDefault().language.equals(language.language)) {
                group.clearSelection()
                isSelected = true
                actionPerformed(null)
            }
        }

        override fun actionPerformed(e: ActionEvent?) {
            Locale.setDefault(language)
            ResourceBundle.clearCache()
            bundle = ResourceBundle.getBundle("language", language)
            for (property in properties)
                property.value.value = bundle.getString(property.key)
        }

    }

}