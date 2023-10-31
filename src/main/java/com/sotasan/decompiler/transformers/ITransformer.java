package com.sotasan.decompiler.transformers;

import com.sotasan.decompiler.models.FileModel;
import java.util.concurrent.CompletableFuture;

public interface ITransformer {

    CompletableFuture<String> transform(FileModel fileModel) throws Exception;

}