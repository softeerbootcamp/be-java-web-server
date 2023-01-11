package request.methodHandler;

import request.Request;

import java.io.IOException;

public interface HttpMethodHandler {
    void handle(Request request) throws IOException;
}
