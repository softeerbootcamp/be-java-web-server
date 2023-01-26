package bejavawebserver.service;

import bejavawebserver.model.LoginForm;
import bejavawebserver.model.User;
import bejavawebserver.repository.JdbcRepository;
import bejavawebserver.repository.MemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
public class LoginService {
    @Autowired JdbcRepository jdbcRepository;
    public boolean isLoginSuccess(LoginForm loginForm, HttpSession session) {
        User user = jdbcRepository.findUserById(loginForm.getUserId());
        boolean isSuccess = Objects.equals(user.getPassword(), loginForm.getPassword());
        if(isSuccess) session.setAttribute("user", user);
        return isSuccess;
    }


    public static boolean isLogin(HttpSession session) {
        return session != null;
    }


}
