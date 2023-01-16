package controller;

import db.Database;
import http.exception.MethodNotAllowException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.util.Map;

public class SignUpController implements Controller{

    public static final String PATH = "/user/create";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowException();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> queries = request.getQueries();

        User user = new User(
                queries.get("userId"),
                queries.get("password"),
                queries.get("name"),
                queries.get("email")
        );

        Database.addUser(user);
        response.redirect("/user/login.html");
    }
}
