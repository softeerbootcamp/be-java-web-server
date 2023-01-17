package webserver.controller;

import model.request.Request;
import model.response.Response;

import static model.response.HttpStatusCode.FOUND;

public class UserCreateController implements UserController {
    @Override
    public void service(Request request, Response response) {
        userService.signUpUser(request);
        response.setStatusCode(request.getHttpVersion(), FOUND);
        response.addHeader("Location", "/index.html");

    }
}
