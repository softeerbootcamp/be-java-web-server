package controller;

import io.request.HttpRequest;
import io.request.RequestFactory;
import io.response.Response;
import io.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class FacadeController implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(FacadeController.class);
    private final RequestFactory requestFactory = new RequestFactory();
    private final ResponseFactory responseFactory = new ResponseFactory();

    private final Map<Domain, Controller> controllers = Map.of(
            Domain.USER, new UserController(),
            Domain.MAIN, new MainController()
    );

    private Socket connection;

    public FacadeController(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = requestFactory.create(in);
            Response response = responseFactory.create(out);

            delegateRequest(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void delegateRequest(HttpRequest request, Response response) {
        Controller controller = controllers.get(Domain.find(request.getUrl()));

        controller.handle(request, response);
    }
}
