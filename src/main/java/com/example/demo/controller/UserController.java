package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/form")
    public String joinPage() {
        return "user/form";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "user/login";
    }

    @GetMapping("/list")
    public String memberListPage(Model model) {
        return "user/list";
    }

    @PostMapping("/user/create")
    public String GenerateMember(@ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        log.debug("user create post");

        User user = new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
        model.addAttribute("user", user);

        UserService.join(user);

        redirectAttributes.addAttribute("user", user);
        return "redirect:/index";
    }
}
