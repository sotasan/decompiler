package com.hohltier.decompiler.transformers;

import com.hohltier.decompiler.models.FileModel;
import com.strobel.assembler.metadata.Buffer;
import com.strobel.assembler.metadata.ITypeLoader;
import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.ITextOutput;
import com.strobel.decompiler.PlainTextOutput;
import org.jetbrains.annotations.NotNull;

public class ProcyonTransformer implements ITransformer, ITypeLoader {

    @Override
    public String transform(@NotNull FileModel fileModel) {
        DecompilerSettings settings = DecompilerSettings.javaDefaults();
        settings.setTypeLoader(this);
        ITextOutput output = new PlainTextOutput();
        Decompiler.decompile(fileModel.getName(), output, settings);
        return output.toString();
    }

    @Override
    public boolean tryLoadType(String internalName, Buffer buffer) {
        System.out.println("TODO");
        return false;
    }

}