package session;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpSessionManagerTest {

    @Test
    @DisplayName("존재하지 않는 세션을 조회하는 경우 NULL값으로 떨어지는지 확인 ")
    void getSession() {
        //given
        User user = User.of("userId","1234","name","kkk@gmail.com");
        HttpSession session = HttpSessionManager.createSession(user);
        String id = session.getId();

        //when
        //HttpSessionManager.addSession(session);

        //then
        assertThat(HttpSessionManager.getSession(id)).isNull();
    }

    @Test
    @DisplayName("세션 생성하기")
    void createSession() {
        //given
        User user = User.of("userId","1234","name","kkk@gmail.com");
        //when
        HttpSession session = HttpSessionManager.createSession(user);
        //then
        assertAll(
                () -> assertEquals("userId",session.getUser().getUserId()),
                () -> assertEquals("1234",session.getUser().getPassword()),
                () -> assertEquals("name",session.getUser().getName()),
                () -> assertEquals("kkk@gmail.com",session.getUser().getEmail())
        );

    }

    @Test
    @DisplayName("세션이 관리 목록에 정상적으로 들어가는지 확인")
    void addSession() {
        //given
        User user = User.of("userId","1234","name","kkk@gmail.com");
        HttpSession session = HttpSessionManager.createSession(user);
        String id = session.getId();

        //when
        HttpSessionManager.addSession(session);

        //then
        assertThat(HttpSessionManager.getSession(id)).isNotNull();
    }
}
