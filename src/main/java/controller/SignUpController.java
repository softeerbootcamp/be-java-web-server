package controller;

import db.Database;
import http.exception.MethodNotAllowException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SignUpController implements Controller{

    public static final String PATH = "/user/create";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        Map<String, String> queries = request.getQueries();

        User user = new User(
                queries.get("userId"),
                queries.get("password"),
                queries.get("name"),
                URLDecoder.decode(queries.get("email"), StandardCharsets.UTF_8)
        );

        Database.addUser(user);
        response.redirect("/user/login.html");
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowException();
    }
}
