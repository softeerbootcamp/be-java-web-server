package bejavawebserver.service;

import bejavawebserver.model.LoginForm;
import bejavawebserver.repository.memoryRepository;
import bejavawebserver.model.User;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class LoginService {
    public static boolean isLoginSuccess(LoginForm loginForm, HttpSession session){
        User user = memoryRepository.findUserById(loginForm.getUserId());
        return Objects.equals(user.getPassword(), loginForm.getPassword());
    }


}
