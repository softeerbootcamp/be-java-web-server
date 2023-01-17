package webserver.controller;

import model.request.Request;
import model.response.Response;

import java.util.Map;

public class UserLoginController implements UserController{
    @Override
    public void service(Request request, Response response) {
        Map<String, String> requestParams = request.getRequestParams();
    }
}
