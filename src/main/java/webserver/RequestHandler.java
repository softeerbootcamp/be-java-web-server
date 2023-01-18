package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestGenerator;

import java.io.*;
import java.net.Socket;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = HttpRequestGenerator.generateHttpMessage(br);
            HttpResponse httpResponse = new HttpResponse(httpRequest.getVersion(), dos);
            Dispatcher.dispatch(httpRequest, httpResponse);
            httpResponse.send();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
