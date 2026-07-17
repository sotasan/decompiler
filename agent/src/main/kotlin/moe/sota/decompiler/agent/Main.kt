@file:JvmName("Main")

package moe.sota.decompiler.agent

import java.lang.instrument.Instrumentation

fun agentmain(agentArgs: String?, inst: Instrumentation?) {
    System.out.println("Hello World!")
}
