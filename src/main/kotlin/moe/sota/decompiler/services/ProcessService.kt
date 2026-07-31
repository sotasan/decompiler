package moe.sota.decompiler.services

import java.lang.management.ManagementFactory
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.jvm.javaMethod
import moe.sota.decompiler.main

class ProcessService {
    fun start() {
        val java = ProcessHandle.current().info().command().getOrNull() ?: return
        val classPath = ManagementFactory.getRuntimeMXBean().classPath
        val mainClass = ::main.javaMethod?.declaringClass?.canonicalName
        ProcessBuilder(java, "-cp", classPath, mainClass).start()
    }
}
