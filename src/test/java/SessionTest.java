import db.SessionDatabase;
import model.Session;
import model.User;
import org.junit.Assert;
import org.junit.Test;
import service.SessionService;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {
    @Test
    public void 세션_삽입() {
        try {
            SessionService service = SessionService.getInstance();
            SessionDatabase db = SessionDatabase.getInstance();
            User user = User.of("jin", "jin", "jin", "jin");
            Session session = Session.of("uuid", user.getId());
            service.addSession(session);
            assertThat(db.existsBySessionId("uuid")).isEqualTo(true);
        } catch (SQLException | NullPointerException e) {
            Assert.fail();
        }
    }

    @Test
    public void 세션_유효() {
        try {
            Session session1 = Session.of("123", "jin");
            SessionService.getInstance().addSession(session1);

            assertThat(SessionService.getInstance().isValid("123")).isEqualTo(true);
            assertThat(SessionService.getInstance().isValid("")).isEqualTo(false);
        } catch (SQLException | NullPointerException e) {
            Assert.fail();
        }
    }
}
