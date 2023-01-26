
package webserver.utils;

import db.tmpl.SessionDatabase;
import db.tmpl.UserDatabase;
import model.HttpSession;
import model.User;
import model.UserPrincipal;
import webserver.CustomApplicationContext;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.exception.HttpRequestException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class HttpSessionUtils {

    private UserDatabase userDatabase;
    private SessionDatabase sessionDatabase;

    private HttpSessionUtils(){}

    public static HttpSessionUtils getInstance(){
        return HttpSessionUtils.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final HttpSessionUtils INSTANCE = new HttpSessionUtils();
    }

    public void setUserDatabase(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
    }

    public void setSessionDatabase(SessionDatabase sessionDatabase){
        this.sessionDatabase = sessionDatabase;
    }


    public HttpSession generateSession(String userid){

        String sessionStr = UUID.randomUUID().toString();
        User user = userDatabase.findUserById(userid).orElse(null);
        HttpSession session = HttpSession.of(sessionStr, userid, user.getName(), LocalDateTime.now());
        sessionDatabase.addCookie(session);
        return session;
        }

    public Optional<HttpSession> sessionUpdate (String sessionId){
        HttpSession session = sessionDatabase.findSessionById(sessionId).orElse(null);
        if(session != null){
            if(session.isValid())  //available session
                session.updateCookieTimeInfo(LocalDateTime.now());  //renew lastAccessTime, expireDate, and maxAge of the session
            else   //expired session
                cookieInvalidation(sessionId);
        }
        return Optional.ofNullable(session);
    }


    public String cookieInvalidation(String sessionId){
        StringBuilder newSession = new StringBuilder();
        sessionDatabase.findSessionById(sessionId).ifPresent(session -> {
            session.invalidateSession();
            newSession.append(session.toString());
        });
        return newSession.toString();
    }

    public Optional<String> getSessionIdFromRequest(Request req){
        String session = req.getRequestHeader().get("Cookie");
        if(session == null)
            return Optional.empty();
        String sessionID = session.substring(session.indexOf("=")+1);
        return Optional.of(sessionID);
    }

    public boolean isSessionValid(String sessionId) {
        HttpSession session = sessionDatabase.findSessionById(sessionId).orElse(null);
        if (session != null && session.isValid())
            return true;
        return false;
    }

    public UserPrincipal sessionIdToUserPrincipal(String sessionId) {
        HttpSession session = sessionDatabase.findSessionById(sessionId).orElse(null);
        if(session == null)
            throw HttpRequestException.builder()
                .statusCode(StatusCodes.BAD_REQUEST)
                .msg("<script>alert('로그인 세션이 종료되었습니다.'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>")
                .build();
        return new UserPrincipal(session.getUserId(), session.getUsername());
    }
}
