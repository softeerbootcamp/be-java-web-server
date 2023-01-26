package service;

import db.SessionDB;
import http.common.Cookie;
import http.common.Session;
import http.request.HttpRequest;
import model.User;

import java.util.Optional;

public class AuthService {
    public Boolean isAuthenticated(HttpRequest request) {
        Cookie sessionCookie = request.getCookie(Session.SESSION_FIELD_NAME);
        if (sessionCookie == null) {
            return false;
        }
        Session session = SessionDB.getSession(sessionCookie.getValue());
        if (session == null) {
            return false;
        }
        return session.isValid();
    }

    public Optional<User> getUser(HttpRequest request) {
        Cookie sessionCookie = request.getCookie(Session.SESSION_FIELD_NAME);
        if (sessionCookie == null) {
            return Optional.ofNullable(null);
        }
        Session session = SessionDB.getSession(sessionCookie.getValue());
        if (session == null) {
            return Optional.ofNullable(null);
        }
        return Optional.ofNullable(session.getUser());
    }
}
