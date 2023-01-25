package bejavawebserver.controller;

import bejavawebserver.model.User;
import bejavawebserver.service.LoginService;
import bejavawebserver.service.SignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @PostMapping("/user/create")
    public String signUp(User user){
        SignUpService.addDatabase(user);
        return "redirect:/";
    }

    @PostMapping("/user/login")
    public String login(User user, HttpSession session){
        if(LoginService.loginCheck(user, session)){
            return "redirect:/user/login.html";
        }
        return "redirect:/user/login_failed.html";
    }

}
