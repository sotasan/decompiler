package dev.shota.decompiler.runtime;

public class Test implements Instance<Test> {

    @Override
    public Test getInstance() {
        return this;
    }

}
