
package webserver.utils;

import db.SessionDataBase;
import db.UserDatabase;
import model.HttpSession;
import model.User;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.exception.HttpRequestException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class HttpSessionUtils {


    public static HttpSession generateSession(String userid){

        String sessionStr = UUID.randomUUID().toString();
        User user = UserDatabase.findUserById(userid).orElse(null);
        HttpSession session = new HttpSession(sessionStr, userid, user.getName(), LocalDateTime.now());
        SessionDataBase.addCookie(session);
        return session;
        }

    public static Optional<HttpSession> sessionUpdate (String sessionId){
        HttpSession session = SessionDataBase.findSessionById(sessionId).orElse(null);
        if(session != null){
            if(session.isValid())  //available session
                session.updateCookieTimeInfo(LocalDateTime.now());  //renew lastAccessTime, expireDate, and maxAge of the session
            else   //expired session
                cookieInvalidation(sessionId);
        }
        return Optional.ofNullable(session);
    }


    public static String cookieInvalidation(String sessionId){
        StringBuilder newSession = new StringBuilder();
        SessionDataBase.findSessionById(sessionId).ifPresent(session -> {
            session.invalidateSession();
            newSession.append(session.toString());
        });
        return newSession.toString();
    }

    public static Optional<String> getSessionIdFromRequest(Request req){
        String session = req.getRequestHeader().get("Cookie");
        if(session == null)
            return Optional.empty();
        String sessionID = session.substring(session.indexOf("=")+1);
        return Optional.of(sessionID);
    }

    public static boolean isSessionValid(String sessionId) {
        HttpSession session = SessionDataBase.findSessionById(sessionId).orElse(null);
        if (session != null && session.isValid())
            return true;
        return false;
    }

    public static String sessionIdToUserName(String sessionId){
        HttpSession session = SessionDataBase.findSessionById(sessionId).orElse(null);
        if(session == null)
            throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('로그인 세션이 종료되었습니다.'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>");
        return session.getUsername();
    }
}
