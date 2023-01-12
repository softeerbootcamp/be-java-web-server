package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpParser;
import webserver.controller.ControllerHandler;
import webserver.controller.ControllerHandlerFactory;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
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

            HttpRequest httpRequest = HttpRequest.newInstance(HttpParser.parseHttpRequest(in));
            ControllerHandler controllerHandler = ControllerHandlerFactory.getHandler(httpRequest);

            response(dos, controllerHandler.handle());
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
    private void response200Header(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.response200Headers());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        byte[] body = httpResponse.getBody();

        try {
            if (body == null) {
                dos.flush();
                return;
            }
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
