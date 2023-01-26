package bejavawebserver.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LogoutService {
    public void removeSession(HttpSession session) {
        session.removeAttribute(session.getId());
    }
}
