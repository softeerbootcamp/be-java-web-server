package com.example.demo.controller;

import com.example.demo.db.Database;
import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/index")
    public String mainPage(@CookieValue(name = "userId", required = false) Cookie cookie, Model model){
        log.debug("In mage page");
//        log.debug("cookieName : {}",cookie.getName());

        if(cookie != null){
            String value = cookie.getValue();
            log.debug("value : {}",value);
            model.addAttribute("cookie", value);
            return "indexHome";
        }
//        if (userId == null) return "index";

//        User user = Database.findUserById(userId);
//        if (user == null) return "index";

        return "index";
    }

}
