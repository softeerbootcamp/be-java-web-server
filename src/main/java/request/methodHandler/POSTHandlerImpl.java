package request.methodHandler;

import request.Request;

import java.io.DataOutputStream;
import java.io.IOException;

public class POSTHandlerImpl implements HttpMethodHandler {
    private final DataOutputStream dos;

    public POSTHandlerImpl(DataOutputStream dos) {
        this.dos = dos;
    }
    @Override
    public void handle(Request request) throws IOException {
    }
}
