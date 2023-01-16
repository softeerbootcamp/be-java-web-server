package webserver;

import model.Request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final UserController userController;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.userController = new UserController();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream()) {
            Request request = new Request(in);

            //TODO Controller 고민, 조건에 맞게 처리될 수 있도록
            if (request.getRequestParams().size() != 0) {
                userController.signUp(request);
            }

            ResponseHandler responseHandler = new ResponseHandler(connection);
            responseHandler.response(request);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
