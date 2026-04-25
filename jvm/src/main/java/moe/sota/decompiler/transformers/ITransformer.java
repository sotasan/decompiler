package moe.sota.decompiler.transformers;

import moe.sota.decompiler.models.FileModel;

public interface ITransformer {
    String transform(FileModel fileModel) throws Exception;
}
