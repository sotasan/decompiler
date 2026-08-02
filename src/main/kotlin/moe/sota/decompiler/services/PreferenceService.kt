package moe.sota.decompiler.services

import java.util.prefs.Preferences
import moe.sota.decompiler.Application

object PreferenceService {
    val preferences = Preferences.userNodeForPackage(Application::class.java)
}
