package moe.sota.decompiler.jvm.services;

import java.util.prefs.Preferences;
import lombok.experimental.UtilityClass;
import moe.sota.decompiler.jvm.Main;

@UtilityClass
public class PreferenceService {
    public static final Preferences PREFERENCES = Preferences.userNodeForPackage(Main.class);
}
