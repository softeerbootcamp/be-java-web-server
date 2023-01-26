package db.tmpl;

import model.HttpSession;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SessionDatabase {

    void addCookie(HttpSession session);

    Optional<HttpSession> findSessionById(String sessionId);

    void deleteCookie(String sessionId);

    List<HttpSession> findAll();
}
