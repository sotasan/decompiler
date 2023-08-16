package com.sotasan.decompiler.transformers;

import com.sotasan.decompiler.models.FileModel;

public interface ITransformer {

    String transform(FileModel fileModel) throws Exception;

}