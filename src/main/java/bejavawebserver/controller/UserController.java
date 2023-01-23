package bejavawebserver.controller;

import bejavawebserver.db.memoryDB;
import bejavawebserver.model.User;
import bejavawebserver.service.SignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @PostMapping("/user/create")
    public String signUp(User user){
        SignUpService.addDatabase(user);
        return "redirect:/";
    }
}
