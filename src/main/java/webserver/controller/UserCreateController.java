package webserver.controller;

import model.request.Request;
import model.response.Response;

import java.util.Map;

import static model.response.HttpStatusCode.FOUND;

public class UserCreateController implements UserController {
    @Override
    public Response service(Request request) {
        userService.signUpUser(request);
        return Response.of(request.getHttpVersion(), FOUND, Map.of("Location", "/index.html"), new byte[0]);
    }
}
