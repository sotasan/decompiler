package moe.sota.decompiler.services

import moe.sota.decompiler.models.SearchKind
import moe.sota.decompiler.models.SearchSymbol
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

private const val CONSTANT_STRING = 8
private const val MAX_VALUE_LENGTH = 200

fun indexSymbols(bytes: ByteArray): List<SearchSymbol> {
    val reader = ClassReader(bytes)
    val indexer = SearchIndexer()
    reader.accept(
        indexer,
        ClassReader.SKIP_CODE or ClassReader.SKIP_DEBUG or ClassReader.SKIP_FRAMES,
    )

    return indexer.declarations + readStrings(reader)
}

// Reading the constant pool costs the pool's size, while visiting every method body to catch the
// same constants costs the size of the bytecode.
private fun readStrings(reader: ClassReader): List<SearchSymbol> {
    val buffer = CharArray(reader.maxStringLength)
    val strings = LinkedHashSet<String>()

    for (item in 1..<reader.itemCount) {
        val offset = reader.getItem(item)
        if (offset == 0 || reader.readByte(offset - 1) != CONSTANT_STRING) continue

        val value = reader.readConst(item, buffer) as String
        if (value.isNotBlank()) strings.add(value.take(MAX_VALUE_LENGTH))
    }

    return strings.map { SearchSymbol(SearchKind.STRING, it) }
}

private class SearchIndexer : ClassVisitor(Opcodes.ASM9) {
    val declarations = ArrayList<SearchSymbol>()

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
        return null
    }

    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
    ): MethodVisitor? {
        if (name != "<init>" && name != "<clinit>")
            declarations.add(SearchSymbol(SearchKind.METHOD, name))

        return null
    }
}
