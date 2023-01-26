package controller;

import exception.ConnectionClosedException;
import exception.FileSystemException;
import exception.HttpNotFoundException;
import http.request.HttpRequest;
import http.request.RequestFactory;
import http.response.HttpResponse;
import http.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.exception.EmptyInputException;
import service.exception.NotFoundException;
import service.user.exception.LoginIdDuplicatedException;
import service.user.exception.LoginIdNotExistException;
import service.user.exception.PasswordNotMatchException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import static filesystem.PathResolver.*;

public class Dispatcher implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    private static final RequestFactory requestFactory = new RequestFactory();
    private static final ResponseFactory responseFactory = new ResponseFactory();
    private static final Map<Domain, Controller> controllers = Map.of(
            Domain.USER, new UserController(),
            Domain.MAIN, new MainController(),
            Domain.POST, new PostController()
    );
    private static final Map<Class, Handler> redirectUrls = Map.of(
            HttpNotFoundException.class, ((request, response) -> controllers.get(Domain.MAIN).service(request, response)),
            PasswordNotMatchException.class, ((request, response) -> response.redirect(LOGIN_FAILED_HTML)),
            LoginIdNotExistException.class, ((request, response) -> response.redirect(LOGIN_FAILED_HTML)),
            EmptyInputException.class, ((request, response) -> response.redirect(POST_CREATE_HTML)),
            LoginIdDuplicatedException.class, ((request, response) -> response.redirect(DOMAIN)),
            NotFoundException.class, ((request, response) -> response.redirect(DOMAIN))
    );

    private Socket connection;

    public Dispatcher(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = requestFactory.create(in);
            HttpResponse response = responseFactory.create(out);
            delegateRequest(request, response);
        } catch (IOException | ConnectionClosedException | FileSystemException e) {
            logger.error(e.getMessage());
        }
    }

    private void delegateRequest(HttpRequest request, HttpResponse response) {
        try {
            Controller controller = controllers.get(Domain.find(request.getUrl()));
            controller.service(request, response);
        } catch (RuntimeException e) {
            redirectUrls.get(e.getClass()).handle(request, response);
        }
    }
}
