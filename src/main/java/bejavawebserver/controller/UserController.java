package bejavawebserver.controller;

import bejavawebserver.model.LoginForm;
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
        try{
            SignUpService.addDatabase(user);
        }catch (RuntimeException r){
            return "redirect:/user/form.html";
        }
        return "redirect:/";
    }

    @PostMapping("/user/login")
    public String login(LoginForm loginForm, HttpSession session){
        if(LoginService.isLoginSuccess(loginForm, session)){
            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }

}
