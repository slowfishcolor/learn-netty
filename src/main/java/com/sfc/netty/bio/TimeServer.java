package com.sfc.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Block IO Server
 * New thread for each new request.
 * 1:1 Clients vs. Server Threads
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {

            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }

        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            while (true) {
                // Server main thread block at accept
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
