package bejavawebserver.service;

import bejavawebserver.repository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LogoutService {
    @Autowired
    JdbcRepository jdbcRepository;

    public void removeSession(HttpSession session) {
        session.removeAttribute(session.getId());
    }
}
