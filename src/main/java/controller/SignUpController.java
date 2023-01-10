package controller;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.ResponseWriter;

import java.io.IOException;

public class SignUpController {

    public static HttpResponse createUserController(HttpRequest request) throws IOException {
        String userId = request.getQueryByKey("userId");
        String password = request.getQueryByKey("password");
        String name = request.getQueryByKey("name");
        String email = request.getQueryByKey("email");//1234%40khu.ac.kr
        email.replace("%40","@");
        User user = new User(userId,password,name,email);
        Database.addUser(user);
        HttpResponse response = new HttpResponse();
        String redirectUrl = "/index.html";
        response.redirect(redirectUrl);

        return response;
    }
}
