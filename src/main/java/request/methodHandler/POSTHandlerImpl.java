package request.methodHandler;

import request.Request;

import java.io.IOException;

public class POSTHandlerImpl implements HttpMethodHandler {
    @Override
    public byte[] handle(Request request) throws IOException {
        return new byte[0];
    }
}
