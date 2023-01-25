package bejavawebserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HtmlController {
    @GetMapping("/index.html")
    public String indexHtml(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);

        // 로그인 안되어 있음
        if(session == null) return "index";

        // 로그인 되어 있음
        return "/user/list";

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
