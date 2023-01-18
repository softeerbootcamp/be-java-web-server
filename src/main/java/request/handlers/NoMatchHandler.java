package request.method.POST.handlers;

import request.Request;
import response.HttpResponseStatus;
import response.Response;

public class POSTNoMatchHandler extends POSTHandler {
    private static final POSTNoMatchHandler instance;

    static {
        instance = new POSTNoMatchHandler();
    }

    public static POSTHandler getInstance() {
        return instance;
    }

    @Override
    public Response handle(Request request) {
        return Response.of(HttpResponseStatus.NOT_FOUND);
    }
}
