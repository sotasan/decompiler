package moe.sota.decompiler.transformers

import moe.sota.decompiler.models.FileModel
import org.benf.cfr.reader.api.CfrDriver
import org.benf.cfr.reader.api.ClassFileSource
import org.benf.cfr.reader.api.OutputSinkFactory
import org.benf.cfr.reader.api.OutputSinkFactory.Sink
import org.benf.cfr.reader.api.OutputSinkFactory.SinkClass
import org.benf.cfr.reader.api.OutputSinkFactory.SinkType
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair
import org.benf.cfr.reader.util.getopt.OptionsImpl

class CFRTransformer :
    ITransformer, ClassFileSource, OutputSinkFactory, OutputSinkFactory.Sink<String> {
    private lateinit var fileModel: FileModel
    private lateinit var output: String

    override fun transform(fileModel: FileModel): String {
        this.fileModel = fileModel
        val driver =
            CfrDriver.Builder()
                .withClassFileSource(this)
                .withOptions(OPTIONS)
                .withOutputSink(this)
                .build()
        driver.analyse(listOf(fileModel.path))
        if (output.startsWith("/")) output = output.substring(31)
        return output
    }

    override fun getClassFileContent(path: String): Pair<ByteArray, String> =
        Pair(fileModel.bytes, fileModel.path)

    override fun getPossiblyRenamedPath(path: String): String = path

    @Suppress("UNCHECKED_CAST")
    override fun <T> getSink(sinkType: SinkType, sinkClass: SinkClass): Sink<T> = this as Sink<T>

    override fun write(sinkable: String) {
        output = sinkable
    }

    override fun informAnalysisRelativePathDetail(usePath: String?, classFilePath: String?) {}

    override fun addJar(jarPath: String?): Collection<String>? = null

    override fun getSupportedSinks(
        sinkType: SinkType?,
        available: Collection<SinkClass>?,
    ): List<SinkClass>? = null

    companion object {
        private val OPTIONS =
            mapOf(
                OptionsImpl.DECOMPILE_INNER_CLASSES.name to false.toString(),
                OptionsImpl.RELINK_CONSTANT_STRINGS.name to false.toString(),
                OptionsImpl.REMOVE_INNER_CLASS_SYNTHETICS.name to false.toString(),
                OptionsImpl.SHOW_CFR_VERSION.name to false.toString(),
            )
    }
}
