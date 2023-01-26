package webserver;

import controller.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    public static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) {
        startWebServer(getPortNumber(args));
    }

    private static void run(ServerSocket serverSocket) throws IOException {
        Socket connection;
        while ((connection = serverSocket.accept()) != null) {
            Thread thread = new Thread(new Dispatcher(connection));
            thread.start();
        }
    }

    private static void startWebServer(Integer port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            run(serverSocket);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static Integer getPortNumber(String[] args) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }
        return Integer.parseInt(args[0]);
    }
}
