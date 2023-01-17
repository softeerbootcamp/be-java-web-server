package webserver.controller;

import model.request.Request;
import model.response.Response;

public class UserCreateController implements UserController {
    @Override
    public void service(Request request, Response response) {
        userService.signUpUser(request);
    }

}
