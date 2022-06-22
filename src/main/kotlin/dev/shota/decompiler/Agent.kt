@file:JvmName("Agent")
package dev.shota.decompiler

import java.lang.instrument.Instrumentation

fun agentmain(args: String?, inst: Instrumentation) {
    println("Hello World!")
}