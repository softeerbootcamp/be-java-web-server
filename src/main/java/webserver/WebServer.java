package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.FacadeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) {
        startWebServer(getPortNumber(args));
    }

    private static void run(ServerSocket serverSocket) throws IOException {
        Socket connection;
        while ((connection = serverSocket.accept()) != null) {
            Thread thread = new Thread(new FacadeController(connection));
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
