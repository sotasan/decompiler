package moe.sota.decompiler.agent;

import lombok.experimental.UtilityClass;

import java.lang.instrument.Instrumentation;

@UtilityClass
public class Main {

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Hello World!");
    }

}
