package dev.shota.decompiler.old.sidebar

import dev.shota.decompiler.window.utils.assets
import javafx.scene.image.Image
import java.io.ByteArrayInputStream

enum class Type(icon: String) {

    ARCHIVE("archive.png"),
    PACKAGE("package.png"),
    CLASS("class.png"),
    FILE("file.png"),
    TEXT("text.png"),
    MANIFEST("manifest.png");

    val icon = Image(ByteArrayInputStream(assets("icons/$icon")))

}