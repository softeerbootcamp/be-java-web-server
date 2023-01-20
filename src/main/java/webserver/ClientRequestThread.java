package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import request.RequestHandlerFactory;
import request.RequestMethodEnum;
import response.HttpResponse;

public class ClientRequestThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientRequestThread.class);

    private Socket connection;

    private Request request;

    public ClientRequestThread(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            if(in.available() == 0) {
                return;
            }

            this.request = Request.from(in);

            log();

            RequestHandler handler = RequestHandlerFactory.generateHandler(request);

            for(RequestMethodEnum methodEnum : RequestMethodEnum.values()) {
                if(request.getMethod().equals(methodEnum.getMethod())) {
                    HttpResponse.handleHttpResponse(out, methodEnum.execute(handler, request));
                    return;
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void log() {
        logger.debug("\nhandle request of port {}\n{} {} {}\nheaders: {}\nbody: {}", connection.getPort(),
                request.getMethod(), request.getResource(), request.getVersion(),
                request.getRequestHeader(),
                request.getRequestBody());
    }
}
