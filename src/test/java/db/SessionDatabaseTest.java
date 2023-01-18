package db;

import model.Session;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.Service;
import service.UserService;
import util.Cookie;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SessionDatabaseTest {
    private final String sid = "sid";
    private Database sessionDatabase = new SessionDatabase();

    private Service userService = new UserService();

    final String[] userValue = {"test", "1234", "testName", "test@naver.com"};
    final String[] userKey = {"userId", "password", "name", "email"};


    @Test
    @DisplayName("회원가입 요청시 세션 생성 테스트")
    void signUpFlow () {
        //given
        HashMap<String, String> userMap = new HashMap<>();
        for (int i = 0; i < userKey.length; i++) {
            userMap.put(userKey[i],userValue[i]);
        }
        User user = (User) userService.createModel(userMap);
        //when
        Session session = new Session(user);
        sessionDatabase.addData(session);
        Cookie cookie = new Cookie(Session.SESSION_ID, session.getUuid());
        //then
        Session findSession = (Session) sessionDatabase.findObjectById(session.getUuid()).get();
        assertAll(
                () -> assertEquals(cookie.getValue(), session.getUuid()),
                () -> assertEquals(sessionDatabase.findObjectById(session.getUuid()).isPresent(), true),
                ()->assertEquals(user,findSession.getUser())
        );
    }




}