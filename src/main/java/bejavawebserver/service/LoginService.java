package bejavawebserver.service;

import bejavawebserver.model.LoginForm;
import bejavawebserver.model.User;
import bejavawebserver.repository.memoryRepository;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class LoginService {
    public static boolean isLoginSuccess(LoginForm loginForm, HttpSession session) {
        User user = memoryRepository.findUserById(loginForm.getUserId());
        session.setAttribute("user", user);
        return Objects.equals(user.getPassword(), loginForm.getPassword());
    }

    public static boolean isLogin(HttpSession session) {
        return session != null;
    }


}
