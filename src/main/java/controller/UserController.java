package controller;

import db.Database;
import http.*;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller{

    private static final String path = "user";
    @Override
    public HttpResponse doService(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Uri uri = requestLine.getUri();
        QueryParameters queryParameters = uri.getQueryParameters();

        Database.addUser(User.from(httpRequest.getRequestBody()));

        Map<String, String> headers = new HashMap<>();
        headers.put("Location", "/index.html");
        return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes(), requestLine.getVersion());
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Uri uri = requestLine.getUri();
        return path.equals(uri.getDetachControllerPath());
    }
}
