package controller;

import model.general.Status;
import model.request.RequestLine;
import model.response.Response;

public class UserController implements Controller {
    @Override
    public Response getResponse(RequestLine requestLine) {
        if(requestLine.getUri().startsWith("/user/create")) return createUser(requestLine);
        else return Response.from(Status.NOT_FOUND);
    }
}
