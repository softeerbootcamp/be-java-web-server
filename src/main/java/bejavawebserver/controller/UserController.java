package bejavawebserver.controller;

import bejavawebserver.model.LoginForm;
import bejavawebserver.model.User;
import bejavawebserver.service.LoginService;
import bejavawebserver.service.LogoutService;
import bejavawebserver.service.SignUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    SignUpService signUpService;
    @Autowired
    LoginService loginService;
    @Autowired
    LogoutService logoutService;

    @PostMapping("/user/create")
    public String signUp(User user) {
        try {
            signUpService.addDatabase(user);
        } catch (RuntimeException r) {
            logger.debug(r.getMessage());
            return "redirect:/user/form.html";
        }
        return "redirect:/";
    }

    @PostMapping("/user/login")
    public String login(LoginForm loginForm, HttpSession session) {
        if (loginService.isLoginSuccess(loginForm, session)) {
            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        logoutService.removeSession(session);
        return "redirect:/";
    }

}
