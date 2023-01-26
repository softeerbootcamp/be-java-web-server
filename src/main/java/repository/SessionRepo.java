package repository;

import model.Session;

import java.util.Optional;

public interface SessionRepo {
    SessionRepo instance = new MemorySessionRepo();

    static SessionRepo get(){
        return instance;
    }

    Optional<Session> findBySSID(String ssid);

    void addSession(Session session);

    void deleteBySSID(String ssid);

}
