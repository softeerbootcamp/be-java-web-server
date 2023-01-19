package kr.codesquad.webserver.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.session.Session;
import webserver.session.SessionConst;
import webserver.session.SessionManager;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionManagerTest {

    @Test
    @DisplayName("SessionManager가 세션을 정상적으로 생성하여 sessionStore에 저장하는지 검증한다.")
    void createSession() {
        String sessionId = SessionManager.createSession("123");

        boolean isExist = SessionManager.hasSession(sessionId);

        assertThat(isExist).isEqualTo(true);
    }

    @Test
    @DisplayName("SessionManager가 세션을 정상적으로 생성하여 사용자의 아이디를 세션의 속성값으로 저장하는지 검증한다.")
    void createSessionWithUserId() {
        String sessionId = SessionManager.createSession("123");
        Session session = SessionManager.getSession(sessionId);

        String userId = session.getAttribute(SessionConst.USER_ID);

        assertThat(userId).isEqualTo("123");
    }
}
