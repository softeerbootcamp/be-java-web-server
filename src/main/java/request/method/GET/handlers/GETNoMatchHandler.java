package request.method.GET.handlers;

import request.Request;
import response.HttpResponseStatus;
import response.Response;

public class GETNoMatchHandler implements GETHandler {
    private final static GETNoMatchHandler GET_NO_MATCH_HANDLER;

    static {
        GET_NO_MATCH_HANDLER = new GETNoMatchHandler();
    }

    public static GETHandler getInstance() {
        return GET_NO_MATCH_HANDLER;
    }

    @Override
    public Response handle(Request request) {
        return Response.of(HttpResponseStatus.NOT_FOUND);
    }
}
