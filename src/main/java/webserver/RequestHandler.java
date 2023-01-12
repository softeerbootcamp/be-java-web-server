package webserver;

import Controller.Controller;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
            // HttpRequest 클래스에 입력을 받는다.
            HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));

            // HttpRequest 클래스의 URI를 보고 어떤 컨트롤러가 필요한지 골라준다.
            Controller controller = ControllerHandler.handleController(httpRequest);

            // 골라준 컨트롤러로 응답을 만들어 준다.
            HttpResponse httpResponse = controller.makeResponse(httpRequest);

            // 만들어준 응답을 출력
            httpResponse.send(new DataOutputStream(out));

        } catch (IOException e) {
            logger.error("ERROR :  {}", e.getMessage());
        }
    }
}
