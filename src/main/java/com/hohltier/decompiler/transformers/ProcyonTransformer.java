package com.hohltier.decompiler.transformers;

import com.hohltier.decompiler.models.FileModel;
import com.strobel.assembler.metadata.ArrayTypeLoader;
import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.ITextOutput;
import com.strobel.decompiler.PlainTextOutput;
import org.jetbrains.annotations.NotNull;

public class ProcyonTransformer implements ITransformer {

    @Override
    public String transform(@NotNull FileModel fileModel) {
        DecompilerSettings settings = DecompilerSettings.javaDefaults();
        ArrayTypeLoader typeLoader = new ArrayTypeLoader(fileModel.getBytes());
        settings.setTypeLoader(typeLoader);
        ITextOutput output = new PlainTextOutput();
        Decompiler.decompile(typeLoader.getClassNameFromArray(), output, settings);
        return output.toString().trim();
    }

}