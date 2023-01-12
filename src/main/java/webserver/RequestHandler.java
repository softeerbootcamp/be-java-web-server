package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import http.response.HttpResponse;
import http.request.HttpRequest;
import service.UserService;
import utils.HttpRequestGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = HttpRequestGenerator.generateHttpMessage(br);
            HttpResponse httpResponse = new HttpResponse(httpRequest.getVersion());
            Dispatcher.dispatch(httpRequest, httpResponse);
            DataOutputStream dos = new DataOutputStream(out);
            responseHeaderMessage(dos, httpResponse.getHeaderMessage());
            responseBody(dos, httpResponse.getBody());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeaderMessage(DataOutputStream dos, String responseMessage) {
        try {
            dos.writeBytes(responseMessage);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
