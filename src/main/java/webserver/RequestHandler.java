package webserver;

import controller.Controller;
import controller.ControllerFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import static utils.FileIoUtils.load404ErrorFile;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.from(in);

            HttpResponse httpResponse = HttpResponse.createDefaultHttpResponse(out);

            Controller controller = ControllerFactory.findController(httpRequest);

            if(controller == null) {
                byte[] error = load404ErrorFile();
                httpResponse.do404(error);
                return;
            }

            controller.service(httpRequest, httpResponse);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

}
