package service;

import db.Database;
import http.HttpSession;
import http.request.*;
import http.response.HttpResponse;
import http.response.HttpStatus;
import model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {

    public HttpResponse create(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Database.addUser(User.from(httpRequest.getRequestBody()));

        Map<String, String> headers = new HashMap<>();
        headers.put("Location", "/index.html");
        return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes(), requestLine.getVersion());
    }

    public HttpResponse login(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Map<String, String> headers = new HashMap<>();
        String requestId = httpRequest.getRequestBody().get("userId");
        String requestPassword = httpRequest.getRequestBody().get("password");

        if (isExistUser(requestId, requestPassword)) {
            String sessionId = UUID.randomUUID().toString();
            HttpSession.addSession(sessionId);
            headers.put("Location", "/index.html");
            headers.put("Set-Cookie", "sid=" + sessionId + ";" + " Path=/");
            return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes(), requestLine.getVersion());
        }
        headers.put("Location", "/user/login_failed.html");
        return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes(), requestLine.getVersion());
    }

    public HttpResponse list(HttpRequest httpRequest) throws IOException {
        RequestLine requestLine = httpRequest.getRequestLine();
        Map<String, String> headers = new HashMap<>();
        String cookie = httpRequest.getRequestHeader().getHeader("Cookie");
        String sessionId = HttpSession.parseSession(cookie);
        if (HttpSession.validateSession(sessionId)) {
            RequestHeader requestHeader = httpRequest.getRequestHeader();
            HttpUri httpUri = requestLine.getHttpUri();
            ResourceType resourceType = ResourceType.HTML;
            String contentType = requestHeader.getContentType();
            Collection<User> userList = Database.findAll();
            StringBuilder sb = new StringBuilder();
            int idx = 1;
            for (User user : userList) {
                sb.append("<tr>");
                sb.append("<th scope=\"row\">" + idx + "</th>");
                sb.append("<td>" + user.getUserId() + "</td>");
                sb.append("<td>" + user.getName() + "</td>");
                sb.append("<td>" + user.getEmail() + "</td>");
                sb.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
                sb.append("<tr>");
            }
            String fileData = new String(Files.readAllBytes(new File(
                    "src/main/resources" + resourceType.getPath() + "/user/list.html").toPath()));
            fileData = fileData.replace("%userList%", sb.toString());
            byte[] body = fileData.getBytes();
            return HttpResponse.of(HttpStatus.OK, contentType, new HashMap<>(), body, requestLine.getVersion());
        }
        headers.put("Location", "/user/login.html");
        return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes(), requestLine.getVersion());
    }

    private boolean isExistUser(String id, String pw) {
        User user = Database.findUserById(id);
        return user != null && user.getPassword().equals(pw);
    }

}
