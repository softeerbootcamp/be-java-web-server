package com.example.demo.controller;

import com.example.demo.model.LoginDto;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/form")
    public String joinPage(Model model) {
        log.debug("In join page");
        model.addAttribute("userDto", new UserDto());
        return "user/form";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        log.debug("In login page");
        model.addAttribute("loginDto", new LoginDto());
        return "user/login";
    }

    @GetMapping("/list")
    public String memberListPage(Model model) {
        return "user/list";
    }

    @PostMapping("/create.do")
    public String GenerateMember(@ModelAttribute("userDto") UserDto userDto
    ) {
        log.debug("user create post");

        User user = new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
//        User user = new User(userId, password, name, email);
        UserService.join(user);

        log.debug("userId : {}", userDto.getUserId());
        log.debug("password : {}", userDto.getPassword());
        log.debug("name : {}", userDto.getName());
        log.debug("email : {}", userDto.getEmail());

        return "redirect:/index";
    }

    @PostMapping("/login.do")
    public String loginMember(@ModelAttribute("loginDto")LoginDto loginDto) {
        boolean login = UserService.login(loginDto.getUserId(), loginDto.getPassword());
        if(login) return "redirect:/index";
        return "user/login";
    }
}
