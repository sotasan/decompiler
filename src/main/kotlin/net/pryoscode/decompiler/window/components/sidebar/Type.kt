package net.pryoscode.decompiler.window.components.sidebar

import javafx.scene.image.Image

enum class Type(icon: String) {

    ARCHIVE("archive.png"),
    PACKAGE("package.png"),
    META("meta.png"),
    CLASS("class.png"),
    MANIFEST("manifest.png"),
    FILE("file.png"),
    TEXT("text.png");

    val icon = Image(javaClass.classLoader.getResourceAsStream("icons/$icon"))

}