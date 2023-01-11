package controller;

import db.Database;
import http.*;
import model.User;

public class UserController implements Controller{

    private static final String path = "user";
    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Uri uri = requestLine.getUri();
        QueryParameters queryParameters = uri.getQueryParameters();
        Database.addUser(User.from(queryParameters.getParameters()));
        httpResponse.response302Header("/index.html");
        httpResponse.emptyBody();
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Uri uri = requestLine.getUri();
        return path.equals(uri.getDetachControllerPath());
    }
}
