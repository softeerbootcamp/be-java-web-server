package request.method.GET.file;

import request.Request;
import request.method.GET.handlers.GETHandler;
import response.Response;

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
        return null;
    }
}
