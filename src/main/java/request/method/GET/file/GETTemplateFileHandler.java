package request.method.GET.file;

import request.Request;
import request.method.GET.handlers.GETHandler;
import response.Response;

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
        return null;
    }
}
