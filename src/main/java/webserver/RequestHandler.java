package webserver;

import model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final UserService userService;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.userService = new UserService();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream()) {
            Request request = new Request(in);

            if (request.getRequestParams().size() != 0) {
                userService.signUpUser(request);
            }

            ResponseHandler responseHandler = new ResponseHandler(connection);
            responseHandler.response(request);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
