package com.hohltier.decompiler;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.net.Socket;

public class Agent extends Thread {

    private static Socket socket;

    @SneakyThrows
    public static void agentmain(String args, @NotNull Instrumentation inst) {
        socket = new Socket("0.0.0.0", Integer.parseInt(args));
        Runtime.getRuntime().addShutdownHook(new Agent());

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("Hello World!");
        socket.close();

        for (Class<?> clazz : inst.getAllLoadedClasses())
            System.out.println(clazz.getCanonicalName());
    }

    @Override
    public void run() {
        // socket.close();
    }

}