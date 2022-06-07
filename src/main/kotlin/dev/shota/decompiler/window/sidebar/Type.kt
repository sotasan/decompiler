package dev.shota.decompiler.window.sidebar

import javafx.scene.image.Image

enum class Type(icon: String) {

    ARCHIVE("archive.png"),
    PACKAGE("package.png"),
    CLASS("class.png"),
    FILE("file.png"),
    TEXT("text.png"),
    MANIFEST("manifest.png");

    val icon = Image(javaClass.classLoader.getResourceAsStream("icons/$icon"))

}