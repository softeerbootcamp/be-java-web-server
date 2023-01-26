import db.SessionDatabase;
import db.UserDatabase;
import dto.Session;
import dto.SessionCookie;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SessionService;
import service.UserService;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SessionTest {
    private static final Logger logger = LoggerFactory.getLogger(SessionTest.class);

    @BeforeEach
    private void clearUserDatabase() {
        UserDatabase.clear();
        SessionDatabase.clear();
    }

    @Test
    public void Given_User_When_CreateSession_Then_CreateSession() {
        // given
        User existingUser = new User.UserBuilder().setUserId("a").setPassword("a").build();
        // UserService.signUpUser(existingUser);

        // when
        SessionCookie session = SessionService.createSession(existingUser.getUserId(), existingUser.getUsername(), Map.of());

        assertThat(session.toString());

        System.out.print(session);
    }

    @Test
    @DisplayName("Session의 createDate를 DB에 저장 후 읽어온 값이 쓴 값과 일치하는지 테스트")
    public void createDateTest() {
        // given

        // when
        Session expected = SessionService.createSession("gildong", "Hong gildong", Map.of());
        logger.debug("expected uid: {}", expected.getSessionId());

        // then
        Session found = SessionService.findSessionBySID(expected.getSessionId());
        logger.debug("found uid: {}", found.getSessionId());
        assertThat(found.getCreateDate()).isEqualTo(found.getCreateDate());
    }
}
