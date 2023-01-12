package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import response.HttpResponse;
import response.HttpResponseBody;

public class ClientRequestThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientRequestThread.class);

    private final RequestHandler requestHandler;

    private Socket connection;

    private Request request;

    public ClientRequestThread(Socket connectionSocket, RequestHandler requestHandler) {
        this.connection = connectionSocket;
        this.requestHandler = requestHandler;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            if(in.available() == 0) {
                return;
            }
            this.request = Request.of(in);
            byte[] body = requestHandler.handleRequest(request, connection.getPort());
            if(body == null) {
                HttpResponse.Http404Response(out);
                return;
            }
            HttpResponse.Http200Response(request, out, HttpResponseBody.of(body));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
