package service;

import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.SessionRepo;
import repository.UserRepo;

public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    public static Session addUserToSession(User user) {
        return SessionRepo.createSession(user.getUserId());
    }

    public static User getUserBySessionId(String ssid) {
        Session sess = SessionRepo.findBySSID(ssid);
        if (!SessionRepo.isExpired(ssid)) {
            return UserRepo.findUserById(sess.getUserId());
        }
        return null;
    }

    public static boolean isValidSSID(String ssid) {
        logger.info("SID {} is {} {}", ssid, ssid != null && !SessionRepo.isExpired(ssid) && SessionRepo.findBySSID(ssid) != null, SessionRepo.findBySSID(ssid));
        return ssid != null
                && (SessionRepo.findBySSID(ssid) != null);
    }

    public static Session expireSession(String ssid) {
        if (ssid != null)
            SessionRepo.deleteSession(ssid);
        return Session.EXPIRED;
    }
}
