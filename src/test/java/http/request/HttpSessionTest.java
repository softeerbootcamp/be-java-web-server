package http.request;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpSession Test")
public class HttpSessionTest {

    @Test
    @DisplayName("sessionId - 정상 발급 테스트")
    void sessionId() {
        // given
        User sol = new User("1", "1234", "sol", "sol@sol.com");
        HttpSession session = new HttpSession(sol);

        // when
        String sessionId = session.sessionId();

        // then
        assertNotNull(sessionId);
    }

    @Test
    @DisplayName("sessionId - 이미 존재하는 경우 발급하지 않는 테스트")
    void sessionIdWhenExist() {
        // given
        User sol = new User("1", "1234", "sol", "sol@sol.com");
        HttpSession session = new HttpSession(sol);

        // when
        String sessionId = session.sessionId();
        String reissue = session.sessionId();

        // then
        assertEquals(reissue, sessionId);
    }

    @Test
    @DisplayName("reissueSID - 이미 존재하는 경우 발급하지 않는 테스트")
    void reissueSID() {
        // given
        User sol = new User("1", "1234", "sol", "sol@sol.com");
        HttpSession session = new HttpSession(sol);

        // when
        String sessionId = session.sessionId();
        String reissue = session.reissueSID();

        // then
        assertAll(
                () -> assertNotEquals(reissue, sessionId)
        );
    }
}
