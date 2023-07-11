package com.hohltier.decompiler.utils;

import lombok.experimental.UtilityClass;
import javax.swing.*;
import java.awt.*;

@UtilityClass
public class UIUtils {

    public static Font getDefaultFont() {
        return ((Font) UIManager.get("defaultFont"));
    }

}