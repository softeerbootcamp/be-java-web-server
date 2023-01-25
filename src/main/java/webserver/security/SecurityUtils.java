package webserver.security;

import db.SessionDataBase;
import db.UserDatabase;
import model.HttpSession;
import model.User;
import model.UserPrincipal;

public class SecurityUtils {

    public static UserPrincipal getUserPrincipalFromCookie(String sessionId){
        HttpSession httpSession = SessionDataBase.findSessionById(sessionId).orElse(null);
        return new UserPrincipal(httpSession.getUserId(), httpSession.getUsername());
    }
}
