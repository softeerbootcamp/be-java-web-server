package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpParser;
import webserver.domain.HttpResponse;
import webserver.handler.ControllerHandler;
import webserver.handler.ControllerHandlerFactory;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponseMessage;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);

            try {
                HttpRequest httpRequest = HttpRequest.newInstance(HttpParser.parseHttpRequest(in));
                ControllerHandler controllerHandler = ControllerHandlerFactory.getHandler(httpRequest);
                response(dos, controllerHandler.handle(httpRequest));
            } catch (NullPointerException e) {
                logger.error(e.getMessage());
                HttpResponse httpResponse = new HttpResponse();
                response(dos, new HttpResponseMessage(httpResponse.create404Message(), httpResponse.getBody()));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    private void response(DataOutputStream dos, HttpResponseMessage httpResponseMessage) {
        try {
            byte[] body = httpResponseMessage.getBody();
            dos.writeBytes(httpResponseMessage.getHeader());
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
