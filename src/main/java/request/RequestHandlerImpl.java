package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.method.HttpMethod;
import response.Response;

public class RequestHandlerImpl implements RequestHandler {
    @Override
    public Response handle(Request request) {
        for (HttpMethod method : HttpMethod.values()) {
            if (request.getMethod().equals(method.getMethod())) {
                return method.handleRequest(request);
            }
        }

        return null;
    }
}
