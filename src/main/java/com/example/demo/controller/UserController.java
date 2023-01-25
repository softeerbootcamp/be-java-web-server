package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger("UserController.class");

    @GetMapping("/form")
    public String joinPage(Model model) {

        return "/user/form";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "user/login";
    }

    @GetMapping("/list")
    public String memberListPage(Model model) {
        return "/user/list";
    }
}
