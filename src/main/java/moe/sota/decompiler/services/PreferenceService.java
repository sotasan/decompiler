package moe.sota.decompiler.services;

import java.util.prefs.Preferences;
import lombok.experimental.UtilityClass;
import moe.sota.decompiler.Main;

@UtilityClass
public class PreferenceService {
    public static final Preferences PREFERENCES = Preferences.userNodeForPackage(Main.class);
}
