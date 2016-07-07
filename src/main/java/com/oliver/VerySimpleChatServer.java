package com.oliver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanli_000 on 16/7/7.
 */
public class VerySimpleChatServer {
    private final static Logger logger = LoggerFactory.getLogger(VerySimpleChatServer.class);

    private List<PrintWriter> clientOutputStreams;

    public static void main(String[] args) {
        VerySimpleChatServer verySimpleChatServer = new VerySimpleChatServer();
        verySimpleChatServer.go();
    }

    public void go() {
        logger.info("server started!");
        clientOutputStreams = new ArrayList<PrintWriter>();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                logger.info("got a new connection from {}, now have {} connections!", clientSocket.getRemoteSocketAddress(), clientOutputStreams.size());
            }
        } catch (Exception ex) {
            logger.error(Throwables.getStackTraceAsString(ex));
        }
    }

    public void tellEveryone(String message) {
        logger.info("tell {} about message = {}", clientOutputStreams.size(), message);
        for (PrintWriter clientOutputStream : clientOutputStreams) {
            clientOutputStream.println(message);
            clientOutputStream.flush();
        }
    }

    public class ClientHandler implements Runnable {
        Socket socket;
        BufferedReader reader;

        public ClientHandler(Socket socket) {
            try {
                this.socket = socket;
                this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                logger.error(Throwables.getStackTraceAsString(e));
            }
        }

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    logger.info("got message = {}", message);
                    tellEveryone(message);
                }
            } catch (Exception ex) {
                logger.error(Throwables.getStackTraceAsString(ex));
            }
        }
    }
}
