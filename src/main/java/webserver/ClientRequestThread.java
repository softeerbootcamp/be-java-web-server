package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.RequestHandler;

public class ClientRequestThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientRequestThread.class);

    private final RequestHandler requestHandler;

    private Socket connection;

    public ClientRequestThread(Socket connectionSocket, RequestHandler requestHandler) {
        this.connection = connectionSocket;
        this.requestHandler = requestHandler;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            if(in.available() == 0) {
                return;
            }
            requestHandler.handleRequest(in, dos, connection.getPort());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
