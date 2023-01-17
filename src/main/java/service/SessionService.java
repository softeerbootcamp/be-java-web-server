package service;

import model.Session;
import model.User;
import repository.SessionRepo;

public class SessionService {

    public static Session addUserToSession(User user) {
        return SessionRepo.createSession(user.getUserId());
    }

    public User getUserBySessionId(String ssid) {
        Session sess = SessionRepo.findBySSID(ssid);
        if (!SessionRepo.isExpired(ssid)) {
            assert sess != null;
            return UserService.findUserById(sess.getUserId());
        }
        return null;
    }
}
