package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ClientRequestThread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RequestHandlerImpl implements RequestHandler {
    private final Logger logger = LoggerFactory.getLogger(ClientRequestThread.class);
    @Override
    public void handleRequest(InputStream in, DataOutputStream dos, int port) {
        Request request = Request.of(in);
        try {
            for (HttpMethod method : HttpMethod.values()) {
                if (request.getMethod().equals(method.getMethod())) {
                    logger.debug("handle request {} of port {}, request header {}", request.getMethod(), port, request);
                    method.handle(request, dos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
