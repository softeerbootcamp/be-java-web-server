package service;

import db.Database;
import http.HttpSession;
import http.request.HttpRequest;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.HttpStatus;
import model.User;

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

    private boolean isExistUser(String id, String pw) {
        User user = Database.findUserById(id);
        return user != null && user.getPassword().equals(pw);
    }

}
