package service;

import db.Database;
import http.request.HttpRequest;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.HttpStatus;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    public HttpResponse create(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Database.addUser(User.from(httpRequest.getRequestBody()));
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", "/index.html");
        return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes(), requestLine.getVersion());
    }

}
