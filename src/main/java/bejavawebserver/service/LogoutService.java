package bejavawebserver.service;

import bejavawebserver.model.LoginForm;
import bejavawebserver.model.User;
import bejavawebserver.repository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
public class LogoutService {
    @Autowired
    JdbcRepository jdbcRepository;
    public void removeSession(HttpSession session) {
        session.removeAttribute(session.getId());
    }
}
