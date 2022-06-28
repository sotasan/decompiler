package dev.shota.decompiler.window.menu.view.items

import dev.shota.decompiler.window.utils.preferences
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
        generate()

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

    private fun generate() {
        val language = preferences.get("language", Locale.getDefault().language)
        Locale.setDefault(Locale(language))

        for (i in 0 until itemCount) {
            val translation = getItem(i) as Translation
            if (translation.language.language == Locale.getDefault().language)
                translation.isSelected = true
        }
        ResourceBundle.clearCache()
        bundle = ResourceBundle.getBundle("language", Locale.getDefault())
        for (property in properties)
            property.value.value = bundle.getString(property.key)
    }

    private class Translation(val language: Locale, default: Boolean = false) : JRadioButtonMenuItem(language.getDisplayName(language), default), ActionListener {

        init {
            group.add(this)
            addActionListener(this)
        }

        override fun actionPerformed(e: ActionEvent?) {
            preferences.put("language", language.language)
            preferences.flush()
            generate()
        }

    }

}