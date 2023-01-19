package controller;

import model.general.Status;
import model.request.Request;
import model.response.Response;
import model.response.StatusLine;

public class NotFoundController implements Controller {
    @Override
    public Response getResponse(Request request) {
        return Response.from(StatusLine.of(request.getRequestLine().getHttpVersion(), Status.NOT_FOUND));
    }
}
