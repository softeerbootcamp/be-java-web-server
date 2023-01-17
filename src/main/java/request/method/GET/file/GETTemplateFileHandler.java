package request.method.GET.file;

import request.Request;
import request.method.GET.handlers.GETHandler;
import response.HttpResponseStatus;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GETTemplateFileHandler implements GETHandler {

    private static final GETHandler GETTemplateFileHandler;

    static {
        GETTemplateFileHandler = new GETTemplateFileHandler();
    }

    public static GETHandler getInstance() {
        return GETTemplateFileHandler;
    }

    @Override
    public Response handle(Request request) {
        try {
            byte[] file = Files.readAllBytes(new File("src/main/resources/templates" + request.getResource()).toPath());
            return Response.of(file, HttpResponseStatus.OK.getMessage(), HttpResponseStatus.OK.getCode());
        } catch (IOException e) {
            return Response.of(HttpResponseStatus.NOT_FOUND.getMessage(), HttpResponseStatus.NOT_FOUND.getCode());
        }
    }
}
