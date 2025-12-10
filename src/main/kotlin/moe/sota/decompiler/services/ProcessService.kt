package moe.sota.decompiler.services

import moe.sota.decompiler.main
import java.lang.management.ManagementFactory

class ProcessService {

    fun start() {
        val java = ProcessHandle.current().info().command()
        if (java.isPresent) {
            val classPath: String? = ManagementFactory.getRuntimeMXBean().classPath
            val main = ::main.javaClass.canonicalName
            ProcessBuilder(java.get(), "-cp", classPath, main).start()
        }
    }

}
