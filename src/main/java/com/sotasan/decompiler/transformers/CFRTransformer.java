package com.sotasan.decompiler.transformers;

import com.sotasan.decompiler.models.FileModel;
import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.api.OutputSinkFactory;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;
import org.benf.cfr.reader.util.getopt.OptionsImpl;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CFRTransformer implements ITransformer, ClassFileSource, OutputSinkFactory, OutputSinkFactory.Sink<String> {

    private static final Map<String, String> OPTIONS = Map.of(
            OptionsImpl.DECOMPILE_INNER_CLASSES.getName(), String.valueOf(false),
            OptionsImpl.RELINK_CONSTANT_STRINGS.getName(), String.valueOf(false),
            OptionsImpl.REMOVE_INNER_CLASS_SYNTHETICS.getName(), String.valueOf(false),
            OptionsImpl.SHOW_CFR_VERSION.getName(), String.valueOf(false)
    );

    private FileModel fileModel;
    private String output;

    @Override
    public CompletableFuture<String> transform(@NotNull FileModel fileModel) {
        return CompletableFuture.supplyAsync(() -> {
            this.fileModel = fileModel;
            CfrDriver driver = new CfrDriver.Builder().withClassFileSource(this).withOptions(OPTIONS).withOutputSink(this).build();
            driver.analyse(Collections.singletonList(fileModel.getPath()));
            if (output.startsWith("/"))
                output = output.substring(30);
            return output.trim();
        });
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