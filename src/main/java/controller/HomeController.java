package controller;

import model.request.RequestLine;
import model.response.Response;

public class HomeController implements Controller {
    private static final String fileParentPath = "src/main/resources/templates";

    @Override
    public Response getResponse(RequestLine requestLine) {
        return Response.of()
    }
}
