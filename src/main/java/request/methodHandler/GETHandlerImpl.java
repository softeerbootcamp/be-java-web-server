package request.methodHandler;

import model.User;
import request.Request;
import request.RequestParser;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class GETHandlerImpl implements HttpMethodHandler {
    private final DataOutputStream dos;
    private final String USER_REGISTER_URL = "/user/create";

    public GETHandlerImpl(DataOutputStream dos) {
        this.dos = dos;
    }

    @Override
    public void handle(Request request) throws IOException {
        handleGET(request);
    }

    private void handleGET(Request request) throws IOException {
        if(request.getResource().startsWith(USER_REGISTER_URL)) {
            Map<String, String> parsedGETQueryString = RequestParser.parseGETQueryString(request.getResource());
            User user = new User(parsedGETQueryString);

            return;
        }

        Files.readAllBytes(new File("src/main/resources/"
                + (request.getResource().contains(".html") || request.getResource().contains(".ico")
                ? "templates" : "static") + request.getResource()).toPath());
    }
}
