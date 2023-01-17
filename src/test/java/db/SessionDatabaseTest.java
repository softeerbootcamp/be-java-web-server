package db;

import model.Session;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.Cookie;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SessionDatabaseTest {
    private final String sid = "sid";
    private SessionDatabase sessionDatabase = new SessionDatabase();


    @Test
    @DisplayName("쿠키를 사용한 세션 저장 및 조회")
    void sessionFlow() {
        //given
        Cookie cookie = new Cookie(sid, UUID.randomUUID().toString());
        //when
        sessionDatabase.addData(new Session(cookie.getValue()));
        //then
        assertThat(cookie.getValue()).isEqualTo(sessionDatabase.findObjectById(cookie.getValue()).get().getUuid());
    }


}