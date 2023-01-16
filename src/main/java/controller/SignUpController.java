package controller;

import db.Database;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.util.Map;

public class SignUpController implements Controller{

    public static final String PATH = "/user/create";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> data = request.getDatas();

        User user = new User(
                data.get("userId"),
                data.get("password"),
                data.get("name"),
                data.get("email")
        );

        Database.addUser(user);
        response.redirect("/user/login.html");
    }
}
