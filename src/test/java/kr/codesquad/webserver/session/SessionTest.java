package kr.codesquad.webserver.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.session.Session;
import webserver.session.SessionConst;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class SessionTest {

    @Test
    @DisplayName("세션을 생성하는 정적 팩토리 메서드 of()가 정상적으로 세션을 반환하는지 검증한다.")
    void createSession() {
        Session session = Session.of("abc", "123");
        String sessionId = session.getSessionId();
        String userId = session.getAttribute(SessionConst.USER_ID);

        assertAll(
                () -> assertThat(sessionId).isEqualTo("abc"),
                () -> assertThat(userId).isEqualTo("123")
        );
    }

    @Test
    @DisplayName("세션 속성이 정상적으로 추가되는지 검증한다.")
    void setAttribute() {
        Session session = Session.of("abc", "123");
        String key = "time";
        String value = "2022.01.18";

        session.setAttribute(key, value);

        assertThat(session.getAttribute(key)).isEqualTo(value);
    }
}
