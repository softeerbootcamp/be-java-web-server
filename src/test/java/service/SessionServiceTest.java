package service;

import db.SessionDatabase;
import model.Session;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Cookie;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {
    final String[] userValue = {"test", "1234", "testName", "test@naver.com"};
    private SessionService sessionService = new SessionService();
    @Test
    void isUserLoggedIn() {
        //given
        SessionDatabase sessionDatabase = new SessionDatabase();
        User user = new User(userValue[0], userValue[1], userValue[2], userValue[3]);
        Session session = new Session(user);
        sessionDatabase.addData(session);
        Cookie cookie = new Cookie(Session.SESSION_ID, session.getUuid());
        //when
        boolean result = sessionService.isUserLoggedIn(cookie);
        //then
        assertThat(result).isTrue();
    }

    @Test
    void persistUser() {
    }
}