package service;

import db.SessionDB;
import http.common.Cookie;
import http.common.Session;
import http.request.HttpRequest;
import model.User;
import service.exception.NotFoundException;

public class AuthService {
    public Boolean isAuthenticated(HttpRequest request) {
        try {
            return getSession(request).isValid();
        } catch (NotFoundException e) {
            return false;
        }
    }

    public User getUser(HttpRequest request) {
        return getSession(request).getUser();
    }

    private Session getSession(HttpRequest request) {
        Cookie sessionCookie = request.getCookie(Session.SESSION_FIELD_NAME).orElseThrow(() -> new NotFoundException("sessionCookie not found"));
        Session session = SessionDB.getSession(sessionCookie.getValue()).orElseThrow(() -> new NotFoundException("session not found"));
        return session;
    }
}
