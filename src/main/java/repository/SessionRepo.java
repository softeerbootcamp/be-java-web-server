package repository;

import model.Session;

import java.util.Optional;

public interface SessionRepo {

    Optional<Session> findBySSID(String ssid);

    void addSession(Session session);

    void deleteBySSID(String ssid);

}
