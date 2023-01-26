package bejavawebserver.controller;

import bejavawebserver.model.User;
import bejavawebserver.service.HtmlService;
import bejavawebserver.service.ListService;
import bejavawebserver.service.LoginService;
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
            "/user/login.html",
            "/user/login_failed.html",
            "/user/profile.html"})
    public String indexHtml(HttpServletRequest httpServletRequest, Model model){
        HttpSession session = httpServletRequest.getSession(false);
        String uri = httpServletRequest.getRequestURI();


        // 로그인 상태인 경우
        if(LoginService.isLogin(session)) {
            return HtmlService.makeLoginView(model, uri, (User)session.getAttribute("user"));
        }
        // 로그인 상태가 아닌 경우
        return HtmlService.makeNotLoginView(model, uri);
    }

    @GetMapping("/user/list.html")
    public String listHtml(HttpServletRequest httpServletRequest, Model model){
        HttpSession session = httpServletRequest.getSession(false);
        String uri = httpServletRequest.getRequestURI();

        // 로그인 상태인 경우
        if(LoginService.isLogin(session)) return ListService.makeUserList(model, uri, (User)session.getAttribute("user"));

        // 로그인 상태가 아닌 경우
        return "redirect:/user/login.html";

    }
}
