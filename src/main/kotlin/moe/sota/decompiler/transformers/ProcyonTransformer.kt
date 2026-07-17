package moe.sota.decompiler.transformers

import com.strobel.assembler.metadata.ArrayTypeLoader
import com.strobel.decompiler.Decompiler
import com.strobel.decompiler.DecompilerSettings
import com.strobel.decompiler.PlainTextOutput
import moe.sota.decompiler.models.FileModel

class ProcyonTransformer : ITransformer {
    override fun transform(fileModel: FileModel): String {
        val settings = DecompilerSettings.javaDefaults()
        val typeLoader = ArrayTypeLoader(fileModel.bytes)
        settings.typeLoader = typeLoader
        val output = PlainTextOutput()
        Decompiler.decompile(typeLoader.classNameFromArray, output, settings)
        return output.toString()
    }
}
