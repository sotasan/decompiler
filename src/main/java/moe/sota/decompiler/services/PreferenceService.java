package moe.sota.decompiler.services;

import lombok.experimental.UtilityClass;
import moe.sota.decompiler.Main;

import java.util.prefs.Preferences;

@UtilityClass
public class PreferenceService {

    public static final Preferences PREFERENCES = Preferences.userNodeForPackage(Main.class);

}
