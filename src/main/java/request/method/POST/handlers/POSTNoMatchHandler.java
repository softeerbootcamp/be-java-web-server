package request.method.POST.handlers;

import request.Request;
import response.HttpResponseStatus;
import response.Response;

public class POSTNoMatchHandler implements POSTHandler {
    private static final POSTNoMatchHandler POST_NO_MATCH_HANDLER;

    static {
        POST_NO_MATCH_HANDLER = new POSTNoMatchHandler();
    }

    public static POSTHandler getInstance() {
        return POST_NO_MATCH_HANDLER;
    }

    @Override
    public Response handle(Request request) {
        return Response.of(HttpResponseStatus.NOT_FOUND.getMessage(), HttpResponseStatus.NOT_FOUND.getCode());
    }
}
