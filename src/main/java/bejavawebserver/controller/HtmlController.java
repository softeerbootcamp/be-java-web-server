package bejavawebserver.controller;

import bejavawebserver.model.User;
import bejavawebserver.repository.memoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HtmlController {
    private static final Logger logger = LoggerFactory.getLogger(HtmlController.class);
    @GetMapping("/index.html")
    public String indexHtml(HttpServletRequest httpServletRequest, Model model){
        HttpSession session = httpServletRequest.getSession(false);

        // 로그인 안되어 있음
        if(session == null) {
            logger.debug("현재 로그인 상태 아님");
            //model.addAttribute("userName", "로그인");
            model.addAttribute("isLogin", false);
            return "index";
        }

        // 로그인 되어 있음
        logger.debug("현재 로그인 상태임");
        User loginUser = (User)session.getAttribute("user");
        model.addAttribute("userName", loginUser.getName());
        model.addAttribute("isLogin", true);

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
