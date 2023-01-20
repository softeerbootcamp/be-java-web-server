package service;

import db.Database;
import db.SessionDatabase;
import model.Session;
import model.User;
import request.RequestHeader;
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

    public boolean cookieValidInSession(RequestHeader requestHeader) {
        Optional<Cookie> cookieOptional = Cookie.extractCookie(requestHeader);
        if (cookieOptional.isEmpty()) {
            return false;
        }
        Cookie cookie = cookieOptional.get();
        if (!this.isUserLoggedIn(cookie)) {
            return false;
        }
        return true;
    }

    public Cookie persistUser(User validUser) {
        Session session = new Session(validUser);
        sessionDatabase.addData(session);
        Cookie cookie = new Cookie(Session.SESSION_ID, session.getUuid());
        return cookie;
    }

}
