package controller;

import db.Database;
import http.*;
import model.User;

public class UserController implements Controller{
    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Uri uri = requestLine.getUri();
        QueryParameters queryParameters = uri.getQueryParameters();
        Database.addUser(User.from(queryParameters.getParameters()));
    }
}
