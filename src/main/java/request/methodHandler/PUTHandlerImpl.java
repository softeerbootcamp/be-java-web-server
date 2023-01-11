package request.methodHandler;

import request.Request;

import java.io.DataOutputStream;
import java.io.IOException;

public class PUTHandlerImpl implements HttpMethodHandler {
    private final DataOutputStream dos;

    public PUTHandlerImpl(DataOutputStream dos) {
        this.dos = dos;
    }
    @Override
    public void handle(Request request) throws IOException {
    }
}
