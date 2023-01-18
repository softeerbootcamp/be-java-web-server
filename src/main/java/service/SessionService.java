package service;

import db.Database;
import db.SessionDatabase;
import util.Cookie;

import java.util.Optional;

public class SessionService {

    private Database sessionDatabase = new SessionDatabase();

    public boolean isUserLoggedIn(Cookie cookie) {
        Optional findSession = sessionDatabase.findObjectById(cookie.getValue());
        if (findSession.isEmpty()) {
            return false;
        }
        return true;

    }

}
