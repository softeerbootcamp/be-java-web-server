package service;

import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.MemorySessionRepo;
import repository.SessionRepo;
import repository.UserRepo;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private static final SessionRepo sessionRepo = new MemorySessionRepo();

    public static Session addUserToSession(User user) {
        Session session = new Session(UUID.randomUUID().toString(), user.getUserId());
        sessionRepo.addSession(session);
        return session;
    }

    public static Optional<User> getUserBySessionId(String ssid) {
        if (isValidSSID(ssid)) {
            Session session = sessionRepo.findBySSID(ssid).get();
            return UserRepo.getInstance().findUserById(session.getUserId());
        }
        return Optional.empty();
    }

    public static boolean isValidSSID(String ssid) {
        Optional<Session> optional = sessionRepo.findBySSID(ssid);
        return ssid != null
                && (optional.isPresent())
                && (optional.get().getExpiredAt().isAfter(LocalDateTime.now()));
    }

    public static Session expireSession(String ssid) {
        if (ssid != null)
            sessionRepo.deleteBySSID(ssid);
        return Session.EXPIRED;
    }
}
