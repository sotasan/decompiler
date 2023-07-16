package com.hohltier.decompiler.transformers;

import com.hohltier.decompiler.models.FileModel;

public interface ITransformer {

    String transform(FileModel fileModel) throws Exception;

}