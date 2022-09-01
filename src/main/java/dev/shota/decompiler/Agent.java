package dev.shota.decompiler;

import org.jetbrains.annotations.NotNull;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.net.Socket;

public class Agent {

    public static void agentmain(String args, @NotNull Instrumentation inst) throws Exception {
        Socket socket = new Socket("0.0.0.0", Integer.parseInt(args));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("Hello World!");
        socket.close();

        for (Class<?> clazz : inst.getAllLoadedClasses())
            System.out.println(clazz.getCanonicalName());
    }

}