package webserver.repository;

import db.SessionStorage;
import exception.SessionNotFoundException;
import model.User;

public class SessionRepository {

    public User findBySessionId(String sid) {
        return SessionStorage.findBySessionId(sid).orElseThrow(SessionNotFoundException::new);
    }
}
