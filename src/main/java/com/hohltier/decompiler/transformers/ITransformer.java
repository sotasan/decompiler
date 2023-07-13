package com.hohltier.decompiler.transformers;

import com.hohltier.decompiler.models.ClassModel;

public interface ITransformer {

    String transform(ClassModel classModel);

}