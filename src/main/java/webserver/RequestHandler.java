package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.FileFinder;
import util.HttpParser;

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

            HttpParser httpParser = new HttpParser();
            HttpRequest httpRequest = HttpRequest.newInstance(httpParser.parseHttpRequest(in));

            String url = httpRequest.getRequestURL();
            HttpResponse httpResponse = null;
            if (url.contains("?")) {
                Map map = httpParser.parseQueryString(url);
                UserService userService = new UserService();
                userService.addUser(map);
                return;
            } else {
                String uri = httpRequest.getRequestUri();
                httpResponse = new HttpResponse(FileFinder.findFile(uri), uri);
            }
            response200Header(dos, httpResponse);
            responseBody(dos, httpResponse);
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
