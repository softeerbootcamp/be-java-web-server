package service;

import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.SessionRepo;

import java.time.LocalDateTime;
import java.util.Optional;

public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    public static Session addUserToSession(User user) {
        return SessionRepo.createSession(user.getUserId());
    }

    public static User getUserBySessionId(String ssid) {
        if (isValidSSID(ssid))
            SessionRepo.findBySSID(ssid);
        return null;
    }

    public static boolean isValidSSID(String ssid) {
        Optional<Session> optional = SessionRepo.findBySSID(ssid);
        return ssid != null
                && (optional.isPresent())
                && (optional.get().getExpiredAt().isAfter(LocalDateTime.now()));
    }

    public static Session expireSession(String ssid) {
        if (ssid != null)
            SessionRepo.deleteSession(ssid);
        return Session.EXPIRED;
    }
}
