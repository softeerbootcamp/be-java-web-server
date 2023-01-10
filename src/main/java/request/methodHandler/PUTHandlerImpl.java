package request.methodHandler;

import request.Request;

import java.io.IOException;

public class PUTHandlerImpl implements HttpMethodHandler {
    @Override
    public byte[] handle(Request request) throws IOException {
        return new byte[0];
    }
}
