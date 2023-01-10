package controller;

import db.Database;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SignUpController implements Controller {

    public static final String PATH = "/user/create";

    @Override
    public void execute(HttpRequest request, HttpResponse response) {
        Map<String, String> querys = request.getQuerys();

        User user = new User(
                querys.get("userId"),
                querys.get("password"),
                querys.get("name"),
                URLDecoder.decode(querys.get("email"), StandardCharsets.UTF_8)
        );

        Database.addUser(user);
        response.redirect("/user/login.html");
    }
}
