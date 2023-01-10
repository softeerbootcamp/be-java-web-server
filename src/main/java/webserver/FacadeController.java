package webserver;

import io.request.Request;
import io.request.RequestFactory;
import io.response.Response;
import io.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class FacadeController implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(FacadeController.class);
    private final RequestFactory requestFactory = new RequestFactory();
    private final ResponseFactory responseFactory = new ResponseFactory();

    private Socket connection;

    public FacadeController(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = requestFactory.create(in);
            Response response = responseFactory.create(request, new DataOutputStream(out));
            response.send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
