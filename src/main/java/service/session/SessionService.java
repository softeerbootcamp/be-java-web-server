package service.session;

import exception.NotLogInException;
import model.Session;
import model.User;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.UUID;

public interface SessionService {
    String createSession(User data);

    Optional<Session> getSession(String sessionId);

    void validateSession(String session) throws NotLogInException;

    void removeSession(String sessionId);

    void checkExpiredSessions();
}
