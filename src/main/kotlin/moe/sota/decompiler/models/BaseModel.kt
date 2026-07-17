package moe.sota.decompiler.models

import java.awt.Image
import java.awt.Toolkit

abstract class BaseModel(val path: String, directory: Boolean) : Comparable<BaseModel> {
    val children: MutableList<BaseModel> = ArrayList()

    var name: String =
        (if (directory) path.substring(0, path.length - 1) else path).let {
            it.substring(it.lastIndexOf('/') + 1)
        }

    var icon: Image? = null
        private set

    fun setIcon(path: String) {
        icon =
            Toolkit.getDefaultToolkit()
                .createImage(javaClass.classLoader.getResourceAsStream(path)!!.readAllBytes())
    }

    override fun compareTo(other: BaseModel): Int {
        val class1 = javaClass
        val class2 = other.javaClass
        return when {
            class1 == class2 -> name.compareTo(other.name, ignoreCase = true)
            class1 == FileModel::class.java -> 1
            class2 == FileModel::class.java -> -1
            else -> 0
        }
    }
}
