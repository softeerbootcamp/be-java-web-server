package service.session;

import model.Session;
import model.User;

import java.util.Optional;
import java.util.UUID;

public interface SessionService {
    String createSession(User data);

    Optional<Session> getSession(String sessionId);

    void removeSession(String sessionId);

    void checkExpiredSessions();
}
