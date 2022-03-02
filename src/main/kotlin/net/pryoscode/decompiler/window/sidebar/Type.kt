package net.pryoscode.decompiler.window.sidebar

import javafx.scene.image.Image

enum class Type(icon: String) {

    ARCHIVE("archive.png"),
    PACKAGE("package.png"),
    CLASS("class.png"),
    FILE("file.png");

    val icon = Image(javaClass.classLoader.getResourceAsStream(icon))

}