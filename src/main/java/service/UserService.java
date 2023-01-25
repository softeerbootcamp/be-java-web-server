package service;

import db.Database;
import model.domain.User;
import model.request.Request;
import model.session.Sessions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

public class UserService {
    private final Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public void signUp(User user) {
        database.addUser(user);
    }

    public boolean logIn(Map<String, String> userLoginInfo) {
        User user = database.findUserById(userLoginInfo.get("userId"));
        return Objects.nonNull(user) && user.getPassword().equals(userLoginInfo.get("password"));
    }

    public User getUser(String userId) {
        return database.findUserById(userId);
    }

    public byte[] getUserListHtmlWhenLogin(Request request) {
        StringBuilder userList = new StringBuilder();
        int row = 0;

        for(User user : database.findAll()) {
            userList.append("<tr><th scope=\"row\">")
                    .append(++row)
                    .append("</th><td>")
                    .append(user.getUserId()).append("</td><td>")
                    .append(user.getName())
                    .append("</td><td>")
                    .append(user.getEmail())
                    .append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
        }

        byte[] body;
        try {
            body = Files.readAllBytes(new File("./src/main/resources/templates/user/list.html").toPath());
        } catch(IOException e) {
            return new byte[0];
        }

        User user = (User) Sessions.getSession(request.getSessionId()).getSessionData().get("user");
        String originalListHtml = new String(body);
        String[] splitListHtml = originalListHtml.split("<tbody>");
        String resultListHtml = splitListHtml[0] + "<tbody>" + userList + splitListHtml[1];
        resultListHtml = resultListHtml.replace("로그인", user.getName());
        resultListHtml = resultListHtml.replace("user/login.html", "user/profile.html");
        return resultListHtml.getBytes();
    }
}
