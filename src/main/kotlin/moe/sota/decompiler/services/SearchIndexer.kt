package moe.sota.decompiler.services

import moe.sota.decompiler.models.SearchKind
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

private const val MAX_VALUE_LENGTH = 200

class SearchSymbol(val kind: SearchKind, val value: String)

class SearchIndexer : ClassVisitor(Opcodes.ASM9) {
    private val declarations = ArrayList<SearchSymbol>()
    private val strings = LinkedHashSet<String>()

    val symbols: List<SearchSymbol>
        get() = declarations + strings.map { SearchSymbol(SearchKind.STRING, it) }

    override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?,
    ) {
        declarations.add(SearchSymbol(SearchKind.CLASS, name.replace('/', '.')))
    }

    override fun visitField(
        access: Int,
        name: String,
        descriptor: String?,
        signature: String?,
        value: Any?,
    ): FieldVisitor? {
        declarations.add(SearchSymbol(SearchKind.FIELD, name))
        addString(value)
        return null
    }

    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
    ): MethodVisitor {
        if (name != "<init>" && name != "<clinit>")
            declarations.add(SearchSymbol(SearchKind.METHOD, name))

        return object : MethodVisitor(Opcodes.ASM9) {
            override fun visitLdcInsn(value: Any?) {
                addString(value)
            }
        }
    }

    private fun addString(value: Any?) {
        if (value is String && value.isNotBlank()) strings.add(value.take(MAX_VALUE_LENGTH))
    }
}
