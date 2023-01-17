package util;

import db.SessionStorage;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import webserver.session.Session;

class SessionTest {
    @Test
    void 세션_유효성_검증_성공() {
        Session session = Session.createSessionWith(new User("ajongs", "password"));
        SessionStorage.addSession(session);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThat(session.isValid()).isEqualTo(true);
    }
    @Test
    void 세션_유효성_검증_실패() {
        Session session = Session.createSessionWith(new User("ajongs", "password"));
        SessionStorage.addSession(session);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThat(session.isValid()).isEqualTo(false);
    }
}