package com.hohltier.decompiler.transformers;

import com.hohltier.decompiler.models.FileModel;
import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.api.OutputSinkFactory;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class CFRTransformer implements ITransformer, ClassFileSource, OutputSinkFactory, OutputSinkFactory.Sink<String> {

    private FileModel fileModel;
    private String output;

    @Override
    public String transform(@NotNull FileModel fileModel) {
        this.fileModel = fileModel;
        CfrDriver driver = new CfrDriver.Builder().withClassFileSource(this).withOutputSink(this).build();
        driver.analyse(Collections.singletonList(fileModel.getPath()));
        return output.substring(37).trim();
    }

    @Override
    public Pair<byte[], String> getClassFileContent(String path) {
        return new Pair<>(fileModel.getBytes(), fileModel.getPath());
    }

    @Override
    public String getPossiblyRenamedPath(String path) {
        return path;
    }

    @Override
    public <T> Sink<T> getSink(SinkType sinkType, SinkClass sinkClass) {
        return (Sink<T>) this;
    }

    @Override
    public void write(String sinkable) {
        output = sinkable;
    }

    @Override public void informAnalysisRelativePathDetail(String usePath, String classFilePath) {}
    @Override public Collection<String> addJar(String jarPath) { return null; }
    @Override public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> available) { return null; }

}