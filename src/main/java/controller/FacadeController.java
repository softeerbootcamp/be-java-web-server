package controller;

import http.request.HttpRequest;
import http.request.RequestFactory;
import http.response.HttpResponse;
import http.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class FacadeController implements Runnable {

    private static final List<String> staticResourceExtensions = List.of(".html", ".css", ".js", "eot", "svg", "ttf", "woff", "woff2", "png");
    private final Logger logger = LoggerFactory.getLogger(FacadeController.class);
    private final RequestFactory requestFactory = new RequestFactory();
    private final ResponseFactory responseFactory = new ResponseFactory();
    private final Map<DomainList, Controller> controllers = Map.of(DomainList.USER, new UserController(), DomainList.MAIN, new MainController());
    private Socket connection;

    public FacadeController(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = requestFactory.create(in);
            HttpResponse response = responseFactory.create(out);
            delegateRequest(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void delegateRequest(HttpRequest request, HttpResponse response) {
        Controller controller = findController(request.getUrl());
        controller.handle(request, response);
    }

    private Controller findController(String url) {
        if (isStaticResourceRequest(url)) {
            return controllers.get(DomainList.MAIN);
        }
        return controllers.get(DomainList.find(url));
    }

    private boolean isStaticResourceRequest(String url) {
        return staticResourceExtensions.stream().anyMatch(url::endsWith);
    }
}
