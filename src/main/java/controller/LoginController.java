package controller;

import db.Database;
import http.common.HttpSessionStorage;
import http.request.HttpRequest;
import http.request.HttpSession;
import http.response.HttpResponse;
import model.User;

import java.util.Map;

public class LoginController implements Controller {
    private static final String LOGIN_FAIL_PATH = "/user/login_failed.html";
    public static final String PATH = "/user/login";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> data = request.getData();

        try {
            User user = Database.findUserById(data.get("userId"));
            if (!user.getPassword().equals(data.get("password"))) {
                response.redirect(LOGIN_FAIL_PATH);
                return;
            }

            HttpSession session = new HttpSession(user);
            HttpSessionStorage.addSession(session);
            response.setCookie("session=" + session.sessionId(), "/");
            response.redirect("/index.html");
        } catch (NullPointerException e) {
            response.redirect(LOGIN_FAIL_PATH);
        }
    }
}
