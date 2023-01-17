package controller;

import db.Database;
import http.request.HttpRequest;
import http.request.HttpUri;
import http.request.QueryParameters;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.HttpStatus;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserController implements Controller{

    private static final String path = "user";
    @Override
    public HttpResponse doService(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        HttpUri httpUri = requestLine.getHttpUri();
        QueryParameters queryParameters = httpUri.getQueryParameters();

        Database.addUser(User.from(httpRequest.getRequestBody()));

        Map<String, String> headers = new HashMap<>();
        headers.put("Location", "/index.html");
        return HttpResponse.of(HttpStatus.FOUND, "", headers, "".getBytes(), requestLine.getVersion());
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        HttpUri httpUri = requestLine.getHttpUri();
        return path.equals(httpUri.getDetachControllerPath());
    }
}
