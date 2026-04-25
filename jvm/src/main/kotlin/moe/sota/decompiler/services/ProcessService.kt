package moe.sota.decompiler.services

import java.lang.management.ManagementFactory
import kotlin.reflect.jvm.javaMethod
import moe.sota.decompiler.main

class ProcessService {
    fun start() {
        val java = ProcessHandle.current().info().command()
        if (java.isPresent) {
            val classPath: String? = ManagementFactory.getRuntimeMXBean().classPath
            val main = ::main.javaMethod?.declaringClass?.canonicalName
            ProcessBuilder(java.get(), "-cp", classPath, main).start()
        }
    }
}
