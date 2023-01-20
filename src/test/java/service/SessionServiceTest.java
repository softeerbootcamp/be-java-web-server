package service;

import db.SessionRepository;
import model.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {

    private final SessionRepository  sessionRepository = new SessionRepository();
    private final SessionService sessionService = new SessionService(sessionRepository);

    @Test
    public void testMakeSession() {

        String userId = "userId";
        String userName = "userName";
        Session session = sessionService.makeSession(userId, userName);

        assertEquals(userId, session.getUserId());
        assertEquals(userName, session.getUserName());
    }

}
