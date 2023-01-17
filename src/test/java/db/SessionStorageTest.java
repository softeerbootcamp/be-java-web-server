package db;

import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Session;

import java.util.HashMap;
import java.util.UUID;


class SessionStorageTest {
    @Test
    void 세션추가테스트() {
        Session session = Session.createSessionWith(new User("ajongs", "password"));
        SessionStorage.addSession(session);

        Session find = SessionStorage.findSessionBy(session.getSid());

        Assertions.assertThat(session).isEqualTo(find);
    }
    @Test
    void UUID() {
        UUID uid1 = UUID.randomUUID();
        UUID uid2 = UUID.fromString(uid1.toString());
        HashMap<UUID, String> map = new HashMap<>();
        map.put(uid1, "1");

        Assertions.assertThat(map.get(uid2)).isEqualTo("1");
    }
}