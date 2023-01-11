package controller;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.ResponseWriter;

import java.io.IOException;

public class SignUpController implements Controller{
    private static final String SIGN_UP_PATH_URL = "/user/create";

    @Override
    public void service(HttpRequest request,HttpResponse response) {
        String userId = request.getQueryByKey("userId");
        String password = request.getQueryByKey("password");
        String name = request.getQueryByKey("name");
        String email = request.getQueryByKey("email");//1234%40khu.ac.kr
        email.replace("%40","@");
        User user = User.of(userId,password,name,email);
        Database.addUser(user);
        String redirectUrl = "/index.html";
        response.redirect(redirectUrl);
    }

    @Override
    public String getPathUrl() {
        return this.SIGN_UP_PATH_URL;
    }
}
