package controller;

import model.general.Status;
import model.request.RequestLine;
import model.response.Response;

public class NotFoundController implements Controller {
    @Override
    public Response getResponse(RequestLine requestLine) {
        return Response.from(Status.NOT_FOUND);
    }
}
