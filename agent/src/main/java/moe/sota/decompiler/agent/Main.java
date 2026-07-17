package moe.sota.decompiler.agent;

import java.lang.instrument.Instrumentation;

public final class Main {

    private Main() {}

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Hello World!");
    }
}
