package service;

import db.Database;
import http.HttpSession;
import http.SessionHandler;
import http.request.HttpRequest;
import http.request.ResourceType;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import model.User;
import util.FileIoUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    public HttpResponse create(HttpRequest httpRequest) {
        Database.addUser(User.from(httpRequest.getRequestBody()));

        return HttpResponseFactory.FOUND("/index.html");
    }

    public HttpResponse login(HttpRequest httpRequest) {
        String requestId = httpRequest.getRequestBody().get("userId");
        String requestPassword = httpRequest.getRequestBody().get("password");

        if (isExistUser(requestId, requestPassword)) {
            User user = Database.findUserById(requestId);
            HttpSession httpSession = SessionHandler.createSession(user);

            HttpResponse httpResponse = HttpResponseFactory.FOUND("/index.html");
            httpResponse.addHeader("Set-Cookie", "sid=" + httpSession.getSid() + "; Path=/");
            return httpResponse;
        }

        return HttpResponseFactory.FOUND("/user/login_failed.html");
    }

    public HttpResponse list(HttpRequest httpRequest) throws IOException {
        Map<String, String> headers = new HashMap<>();
        if (SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());

            Collection<User> userList = Database.findAll();
            StringBuilder sb = new StringBuilder();
            File file = FileIoUtil.getFile(ResourceType.HTML, "/user/list.html");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains("로그인") || line.contains("회원가입")) {
                    continue;
                }
                if(line.contains("Posts")) {
                    sb.append("<li><a>").append(httpSession.getUserName()).append("</a></li>").append(System.lineSeparator());
                }
                if (line.contains("%userList%")) {
                    appendUserList(sb, userList);
                    continue;
                }
                sb.append(line).append(System.lineSeparator());
            }

            return HttpResponseFactory.OK("text/html", headers, sb.toString().getBytes());
        }

        return HttpResponseFactory.FOUND("/user/login.html");
    }

    public HttpResponse logout(HttpRequest httpRequest) {
        if (SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            httpSession.expire();
            return HttpResponseFactory.FOUND("/index.html");
        }
        return HttpResponseFactory.FOUND("/user/login.html");
    }

    private boolean isExistUser(String id, String pw) {
        User user = Database.findUserById(id);
        return user != null && user.getPassword().equals(pw);
    }

    private void appendUserList(StringBuilder sb, Collection<User> users) {
        int idx = 1;
        for (User user : users) {
            sb.append("<tr>");
            sb.append("<th scope=\"row\">").append(idx).append("</th>");
            sb.append("<td>").append(user.getUserId()).append("</td>");
            sb.append("<td>").append(user.getName()).append("</td>");
            sb.append("<td>").append(user.getEmail()).append("</td>");
            sb.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
            sb.append("<tr>");
            idx++;
        }
    }
}
