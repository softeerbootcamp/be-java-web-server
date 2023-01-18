package webserver;

import db.SessionStorage;
import model.User;
import model.request.Request;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.FrontServlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static util.HttpRequestUtils.parseSid;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final FrontServlet frontServlet = new FrontServlet();


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream()) {
            Request request = new Request(in);

            //TODO 세션 유지 방법 고민
            if (request.getHeaders().containsKey("Cookie")) {
                String cookie = request.getHeaders().get("Cookie");
                logger.debug(">> 쿠키 있어요! {}", cookie);
                User user = SessionStorage.findBySessionId(parseSid(cookie)).orElse(User.of());
                logger.debug(">> user id : {}", user.getUserId());
            }

            Response response = frontServlet.process(request);

            ResponseHandler responseHandler = new ResponseHandler(connection);
            responseHandler.response(response);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
