package request.method;

import request.Request;
import response.Response;

import java.io.IOException;

public interface HttpMethodHandler {
    Response handle(Request request) throws IOException;
}
