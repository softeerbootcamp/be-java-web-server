package request.handlers;

import request.Request;
import request.RequestHandler;
import response.HttpResponseStatus;
import response.Response;

public class NoMatchHandler implements RequestHandler {
    private static final NoMatchHandler instance;

    static {
        instance = new NoMatchHandler();
    }

    private NoMatchHandler() {}

    public static NoMatchHandler getInstance() {
        return instance;
    }

    // TODO: 코드의 반복을 없앨 수 있게 리팩토링
    @Override
    public Response doGet(Request request) {
        return Response.from(HttpResponseStatus.NOT_FOUND);
    }

    @Override
    public Response doPost(Request request) {
        return Response.from(HttpResponseStatus.NOT_FOUND);
    }

    @Override
    public Response doPut(Request request) {
        return Response.from(HttpResponseStatus.NOT_FOUND);
    }

    @Override
    public Response doDelete(Request request) {
        return Response.from(HttpResponseStatus.NOT_FOUND);
    }
}
