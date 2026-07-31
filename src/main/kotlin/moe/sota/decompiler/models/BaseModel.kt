package moe.sota.decompiler.models

import java.awt.Image
import java.awt.Toolkit

abstract class BaseModel(val path: String, directory: Boolean) : Comparable<BaseModel> {
    val children = mutableListOf<BaseModel>()

    val name = (if (directory) path.dropLast(1) else path).substringAfterLast('/')

    var icon: Image? = null
        private set

    protected fun loadIcon(path: String) {
        icon =
            Toolkit.getDefaultToolkit()
                .createImage(javaClass.classLoader.getResourceAsStream(path)!!.readBytes())
    }

    override fun compareTo(other: BaseModel): Int =
        when {
            javaClass == other.javaClass -> name.compareTo(other.name, ignoreCase = true)
            this is FileModel -> 1
            other is FileModel -> -1
            else -> 0
        }
}
