package service;

import db.SessionRepository;
import exception.NonLogInException;
import model.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {

    private final SessionRepository sessionRepository = new SessionRepository();
    private final SessionService sessionService = new SessionService(sessionRepository);

    @Test
    @DisplayName("세션이 정상적으로 생성되었는지 테스트")
    public void testMakeSession() {

        String userId = "userId";
        String userName = "userName";
        Session session = sessionService.makeSession(userId, userName);

        assertEquals(userId, session.getUserId());
        assertEquals(userName, session.getUserName());
    }

    @Test
    @DisplayName("세션이 존재하는지 검증하는 메서드 성공 테스트")
    public void testValidateSession() throws NonLogInException {
        String userId = "userId";
        String userName = "userName";
        Session session = sessionService.makeSession(userId, userName);

        sessionService.validateHasSession(session.getSessionId());
    }

    @Test
    @DisplayName("세션이 존재하지 않을 시 예외처리가 정상적으로 되는지 테스트")
    public void testValidateSessionFailed() {
        assertThatThrownBy(
                () -> sessionService.validateHasSession("sessionId"))
                .isInstanceOf(NonLogInException.class)
                .hasMessage("Session not found");
    }

}
