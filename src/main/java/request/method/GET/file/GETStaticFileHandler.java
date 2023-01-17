package request.method.GET.file;

import request.Request;
import request.method.GET.handlers.GETHandler;
import response.HttpResponseStatus;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GETStaticFileHandler implements GETHandler {

    private static final GETHandler GET_STATIC_FILE_HANDLER;

    static {
        GET_STATIC_FILE_HANDLER = new GETStaticFileHandler();
    }

    public static GETHandler getInstance() {
        return GET_STATIC_FILE_HANDLER;
    }

    @Override
    public Response handle(Request request) {
        try {
            byte[] file = Files.readAllBytes(new File("src/main/resources/static" + request.getResource()).toPath());
            return Response.of(file, HttpResponseStatus.OK);
        } catch (IOException e) {
            return Response.of(HttpResponseStatus.NOT_FOUND);
        }
    }
}
