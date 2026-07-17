package moe.sota.decompiler.transformers

import moe.sota.decompiler.models.FileModel
import org.jd.core.v1.ClassFileToJavaSourceDecompiler
import org.jd.core.v1.api.loader.Loader
import org.jd.core.v1.api.printer.Printer

class JDTransformer : ITransformer, Loader, Printer {
    private lateinit var fileModel: FileModel
    private lateinit var builder: StringBuilder
    private var indent = 0

    override fun transform(fileModel: FileModel): String {
        this.fileModel = fileModel
        builder = StringBuilder()
        if (fileModel.path.contains("/")) {
            val pkg = fileModel.path.substring(0, fileModel.path.lastIndexOf('/')).replace('/', '.')
            builder.append("package $pkg;\n\n")
        }
        val decompiler = ClassFileToJavaSourceDecompiler()
        decompiler.decompile(this, this, "")
        return builder.toString()
    }

    override fun canLoad(internalName: String?): Boolean = false

    override fun endLine() {
        builder.append('\n')
    }

    override fun extraLine(count: Int) {
        repeat(count) { builder.append('\n') }
    }

    override fun indent() {
        indent++
    }

    override fun load(internalName: String?): ByteArray = fileModel.bytes

    override fun printDeclaration(
        type: Int,
        internalTypeName: String?,
        name: String?,
        descriptor: String?,
    ) {
        builder.append(name)
    }

    override fun printKeyword(keyword: String?) {
        builder.append(keyword)
    }

    override fun printNumericConstant(constant: String?) {
        builder.append(constant)
    }

    override fun printReference(
        type: Int,
        internalTypeName: String?,
        name: String?,
        descriptor: String?,
        ownerInternalName: String?,
    ) {
        builder.append(name)
    }

    override fun printStringConstant(constant: String?, ownerInternalName: String?) {
        builder.append(constant)
    }

    override fun printText(text: String?) {
        builder.append(text)
    }

    override fun startLine(lineNumber: Int) {
        builder.append("    ".repeat(indent.coerceAtLeast(0)))
    }

    override fun unindent() {
        indent--
    }

    override fun start(maxLineNumber: Int, majorVersion: Int, minorVersion: Int) {}

    override fun startMarker(type: Int) {}

    override fun end() {}

    override fun endMarker(type: Int) {}
}
