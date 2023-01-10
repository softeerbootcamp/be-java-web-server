package request.methodHandler;

import request.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GETHandlerImpl implements HttpMethodHandler {
    @Override
    public byte[] handle(Request request) throws IOException {
        return handleGET(request);
    }

    private byte[] handleGET(Request request) throws IOException {
        return Files.readAllBytes(new File("src/main/resources/"
                + (request.getResource().contains(".html") || request.getResource().contains(".ico") ? "templates" : "static") + request.getResource()).toPath());
    }
}
