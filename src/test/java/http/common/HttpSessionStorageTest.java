package http.common;

import http.request.HttpSession;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpSessionStorage Test")
public class HttpSessionStorageTest {

    @Test
    @DisplayName("alive - 존재하는 경우")
    void alive() {
        // given
        User sol = new User("1", "1234", "sol", "sol@sol.com");
        HttpSession session = new HttpSession(sol);
        HttpSessionStorage.addSession(session);

        // when
        boolean alive = HttpSessionStorage.alive(session);

        // then
        assertTrue(alive);
    }

    @Test
    @DisplayName("alive - 존재하지 않는 경우")
    void notAlive() {
        // given
        User sol = new User("1", "1234", "sol", "sol@sol.com");
        HttpSession session = new HttpSession(sol);

        // when
        boolean alive = HttpSessionStorage.alive(session);

        // then
        assertFalse(alive);
    }

    @Test
    @DisplayName("expire - 세션 죽이기")
    void expire() {
        // given
        User sol = new User("1", "1234", "sol", "sol@sol.com");
        HttpSession session = new HttpSession(sol);
        HttpSessionStorage.addSession(session);

        // when
        boolean expire = HttpSessionStorage.expire(session);

        // then
        assertTrue(expire);
    }

    @Test
    @DisplayName("getSession - 세젼이 존재하는 경우")
    void getSession() {
        // given
        User sol = new User("1", "1234", "sol", "sol@sol.com");
        HttpSession session = new HttpSession(sol);
        HttpSessionStorage.addSession(session);

        // when
        Optional<HttpSession> sessionOptional = HttpSessionStorage.getSession(session.sessionId());

        // then
        assertTrue(sessionOptional.isPresent());
    }

    @Test
    @DisplayName("getSession - 세젼이 존재하지 않는 경우")
    void getSessionWhenNotExist() {
        // given
        User sol = new User("1", "1234", "sol", "sol@sol.com");
        HttpSession session = new HttpSession(sol);
//        HttpSessionStorage.addSession(session);

        // when
        Optional<HttpSession> sessionOptional = HttpSessionStorage.getSession(session.sessionId());

        // then
        assertTrue(sessionOptional.isEmpty());
    }
}
