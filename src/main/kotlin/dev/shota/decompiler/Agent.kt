@file:JvmName("Agent")
package dev.shota.decompiler

import java.io.PrintWriter
import java.lang.instrument.Instrumentation
import java.net.Socket

fun agentmain(args: String?, inst: Instrumentation) {
    val socket = Socket("0.0.0.0", args!!.toInt())

    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            // socket.close()
        }
    })

    val writer = PrintWriter(socket.getOutputStream(), true)
    writer.println("Hello World!")
    socket.close()

    for (clazz in inst.allLoadedClasses)
        println(clazz.canonicalName)
}