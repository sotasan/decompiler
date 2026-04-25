package moe.sota.decompiler.jvm.transformers;

import moe.sota.decompiler.jvm.models.FileModel;

public interface ITransformer {
    String transform(FileModel fileModel) throws Exception;
}
