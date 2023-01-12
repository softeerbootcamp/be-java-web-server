package controller;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.ResponseWriter;

import java.io.IOException;

public class SignUpController extends BaseController{
    private static final String SIGN_UP_PATH_URL = "/user/create";
    // service는 BaseController에서 더 건들이지 않아도 되므로 오버라이딩 하지 않음
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
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

    public String getPathUrl() {
        return this.SIGN_UP_PATH_URL;
    }
}
