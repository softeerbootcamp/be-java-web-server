package db.memoryDB;

import com.google.common.collect.Maps;
import db.tmpl.SessionDatabase;
import model.HttpSession;
import java.util.*;

public class MemorySessionDataBase implements SessionDatabase {

    private static Map<String, HttpSession> sessions = Maps.newHashMap();

    @Override
    public void addCookie(HttpSession session) {
        sessions.put(session.getSessionId(), session);
    }

    @Override
    public Optional<HttpSession> findSessionById(String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    @Override
    public void deleteCookie(String sessionId){
        sessions.remove(sessionId);
    }

    @Override
    public List<HttpSession> findAll() {
        return new ArrayList<>(sessions.values());
    }

}
