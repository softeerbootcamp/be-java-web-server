import db.SessionDatabase;
import model.Session;
import model.User;
import org.junit.Test;
import service.SessionService;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {
    @Test
    public void 세션_삽입() {
        SessionService service = SessionService.getInstance();
        SessionDatabase db = SessionDatabase.getInstance();
        User user = User.of("jin","jin","jin","jin");
        Session session = Session.of("uuid", user);
        service.addSession(session);
        assertThat(db.existsBySessionId("uuid")).isEqualTo(true);
    }
}
