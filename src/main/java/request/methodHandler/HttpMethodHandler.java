package request.methodHandler;

import request.Request;

import java.io.IOException;

public interface HttpMethodHandler {
    byte[] handle(Request request) throws IOException;
}
