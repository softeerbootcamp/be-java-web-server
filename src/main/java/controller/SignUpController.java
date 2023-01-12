package controller;

import db.Database;
import http.common.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SignUpController extends DefaultController {

    public static final String PATH = "/user/create";

    @Override
    public void execute(HttpRequest request, HttpResponse response) {
        if (request.getMethod() == HttpMethod.GET) {
            doGet(request, response);
            return;
        }

        doPost(request, response);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
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

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        super.execute(request, response);
    }
}
