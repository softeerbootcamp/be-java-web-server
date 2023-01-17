package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.method.HttpMethod;
import response.Response;

public class RequestHandlerImpl implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerImpl.class);
    @Override
    public Response handle(Request request, int port) {
        for (HttpMethod method : HttpMethod.values()) {
            if (request.getMethod().equals(method.getMethod())) {
                logger.debug("handle request {} of port {}, request header {}", request.getMethod(), port, request);
                logger.debug("{} {} {}", request.getMethod(), request.getResource(), request.getVersion());
                logger.debug("headers: {}", request.getRequestHeader());
                logger.debug("body: {}", request.getRequestBody());
                return method.handleRequest(request);
            }
        }

        return null;
    }
}
