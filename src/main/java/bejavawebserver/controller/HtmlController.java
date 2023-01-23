package bejavawebserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {
    @GetMapping("/index.html")
    public String indexHtml(){
        return "index";
    }

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

}
