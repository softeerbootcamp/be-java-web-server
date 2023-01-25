package bejavawebserver.controller;

import bejavawebserver.model.User;
import bejavawebserver.repository.memoryRepository;
import bejavawebserver.service.HtmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HtmlController {
    //@GetMapping("/index.html")

    @GetMapping(value = {
            "/index.html",
            "/qna/form.html",
            "/qna/show.html",
            "/user/form.html",
            "/user/list.html",
            "/user/login.html",
            "/user/login_failed.html",
            "/user/profile.html"})
    public String indexHtml(HttpServletRequest httpServletRequest, Model model){
        HttpSession session = httpServletRequest.getSession(false);
        String uri = httpServletRequest.getRequestURI();
        return HtmlService.service(model, uri, session);
    }

    /*
    @GetMapping("/qna/form.html")
    public String quaFormHtml(){
        return "/qna/form";
    }

    @GetMapping("/qna/show.html")
    public String qnaShowHtml(){
        return "/qna/show";
    }

    @GetMapping("/user/form.html")
    public String userFormHtml(){
        return "/user/form";
    }

    @GetMapping("/user/list.html")
    public String userListHtml(){
        return "/user/list";
    }

    @GetMapping("/user/login.html")
    public String userLoginHtml(){
        return "/user/login";
    }

    @GetMapping("/user/login_failed.html")
    public String userLoginFailedHtml(){
        return "/user/login_failed";
    }

    @GetMapping("/user/profile.html")
    public String userProfileHtml(){
        return "/user/profile";
    }

     */

}
