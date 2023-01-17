package request.method.GET.handlers;

import request.Request;
import response.HttpResponseStatus;
import response.Response;

public class GETUserRegisterHandler implements GETHandler{
    private final static GETUserRegisterHandler GET_USER_REGISTER_HANDLER;

    static {
        GET_USER_REGISTER_HANDLER = new GETUserRegisterHandler();
    }

    public static GETHandler getInstance() {
        return GET_USER_REGISTER_HANDLER;
    }

    @Override
    public Response handle(Request request) {
        return Response.of(HttpResponseStatus.NOT_FOUND);
    }
}
