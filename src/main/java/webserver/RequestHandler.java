package webserver;

import java.io.*;
import java.net.Socket;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.util.HttpRequestParser;
import http.util.HttpResponseWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest request = HttpRequestParser.parse(in);
            HttpResponse response = new HttpResponse();

            Dispatcher.handle(request, response);

            HttpResponseWriter.write(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }
}
