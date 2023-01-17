package webserver;

import model.Request.Request;

import java.util.Map;

public class UserController {

    private final UserService userService = new UserService();

    public void signUp(Request request) {
        userService.signUpUser(request);
    }

    //TODO 로그인 구현
    public void login(Request request) {
        Map<String, String> requestParams = request.getRequestParams();
    }
}
