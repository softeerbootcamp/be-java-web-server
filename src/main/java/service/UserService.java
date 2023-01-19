package service;

import db.Database;
import http.HttpSession;
import http.SessionHandler;
import http.request.HttpRequest;
import http.request.RequestLine;
import http.request.ResourceType;
import http.response.HttpResponse;
import http.response.HttpStatus;
import model.User;
import util.FileIoUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    public HttpResponse create(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Database.addUser(User.from(httpRequest.getRequestBody()));

        Map<String, String> headers = new HashMap<>();
        headers.put("Location", "/index.html");
        return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes());
    }

    public HttpResponse login(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Map<String, String> headers = new HashMap<>();
        String requestId = httpRequest.getRequestBody().get("userId");
        String requestPassword = httpRequest.getRequestBody().get("password");

        if (isExistUser(requestId, requestPassword)) {
            User user = Database.findUserById(requestId);
            HttpSession httpSession = SessionHandler.createSession(user);
            headers.put("Location", "/index.html");
            headers.put("Set-Cookie", "sid=" + httpSession.getSid() + ";" + " Path=/");
            return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes());
        }
        headers.put("Location", "/user/login_failed.html");
        return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes());
    }

    public HttpResponse list(HttpRequest httpRequest) throws IOException {
        RequestLine requestLine = httpRequest.getRequestLine();
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
                if(line.contains("%userList%")) {
                    appendUserList(sb, userList);
                    continue;
                }
                sb.append(line).append(System.lineSeparator());
            }


            return HttpResponse.of(
                    HttpStatus.OK,
                    "text/html",
                    headers,
                    sb.toString().getBytes()
            );
        }
        headers.put("Location", "/user/login.html");
        return HttpResponse.of(
                HttpStatus.FOUND,
                "",
                headers,
                "".getBytes()
        );
    }

    public HttpResponse logout(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Map<String, String> headers = new HashMap<>();
        if (SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            httpSession.expire();
            headers.put("Location", "/index.html");
            return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes());
        }
        headers.put("Location", "/user/login.html");
        return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes());
    }

    private boolean isExistUser(String id, String pw) {
        User user = Database.findUserById(id);
        return user != null && user.getPassword().equals(pw);
    }

    private void appendUserList(StringBuilder sb, Collection<User> users) {
        int idx = 1;
        for (User user : users) {
            sb.append("<tr>");
            sb.append("<th scope=\"row\">" + idx + "</th>");
            sb.append("<td>" + user.getUserId() + "</td>");
            sb.append("<td>" + user.getName() + "</td>");
            sb.append("<td>" + user.getEmail() + "</td>");
            sb.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
            sb.append("<tr>");
            idx++;
        }
    }
}
