package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.method.HttpMethod;
import webserver.ClientRequestThread;

import java.io.IOException;

public class RequestHandlerImpl implements RequestHandler {
    private final Logger logger = LoggerFactory.getLogger(ClientRequestThread.class);
    @Override
    public byte[] handleRequest(Request request, int port) {
        try {
            for (HttpMethod method : HttpMethod.values()) {
                if (request.getMethod().equals(method.getMethod())) {
                    logger.debug("handle request {} of port {}, request header {}", request.getMethod(), port, request);
                    return method.handle(request);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
