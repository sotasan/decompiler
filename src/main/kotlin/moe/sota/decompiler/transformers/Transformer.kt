package moe.sota.decompiler.transformers

enum class Transformer(private val factory: () -> ITransformer) {
    CFR(::CFRTransformer),
    JD(::JDTransformer),
    Procyon(::ProcyonTransformer),
    Vineflower(::VineflowerTransformer);

    fun newInstance(): ITransformer = factory()
}
