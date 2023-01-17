package controller;

import service.LoginService;
import service.SignUpService;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class LoginController implements Controller{

    private static final String REDIRECT_URL ="/index.html";
    private static final String FAILED_REDIRECT_URL ="/user/login_failed.html";

    private LoginService service= new LoginService();

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        System.out.println(request.parseBody().entrySet());
        String userId = request.parseBody().get("userId");
        String password = request.parseBody().get("password");
        service.checkRightUser(userId,password);
       // response.redirect(REDIRECT_URL);
        response.redirect(FAILED_REDIRECT_URL);
    }

}
