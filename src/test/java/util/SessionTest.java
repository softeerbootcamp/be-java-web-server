package util;

import model.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @Test
    void newLoginSessionTest() {
        User user = new User("testID","testPW","testName","test%40mail.com");
        String sessionId = Session.newLoginSession(user);
        String userId = Session.loginSession.get(sessionId);
        Assertions.assertThat(userId).isEqualTo(user.getUserId());
    }

}