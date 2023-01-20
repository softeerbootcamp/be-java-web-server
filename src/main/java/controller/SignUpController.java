package controller;

import db.Database;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import view.Model;

import java.util.Map;

public class SignUpController implements Controller{

    public static final String PATH = "/user/create";

    @Override
    public String doPost(HttpRequest request, HttpResponse response, Model model) {
        Map<String, String> data = request.getData();

        User user = new User(
                data.get("userId"),
                data.get("password"),
                data.get("name"),
                data.get("email")
        );

        Database.addUser(user);
        response.redirect("/user/login.html");
        return "";
    }
}
