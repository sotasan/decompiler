package moe.sota.decompiler.agent;

import java.lang.instrument.Instrumentation;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Hello World!");
    }
}
