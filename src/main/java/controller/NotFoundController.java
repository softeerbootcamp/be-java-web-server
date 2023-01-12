package controller;

import model.general.Status;
import model.request.Request;
import model.response.Response;

public class NotFoundController implements Controller {
    @Override
    public Response getResponse(Request request) {
        return Response.from(Status.NOT_FOUND);
    }
}
