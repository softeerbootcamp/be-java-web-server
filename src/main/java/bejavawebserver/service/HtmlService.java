package bejavawebserver.service;

import bejavawebserver.controller.HtmlController;
import bejavawebserver.model.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

@Service
public class HtmlService {
    private static final Logger logger = LoggerFactory.getLogger(HtmlController.class);
    public static String makeNotLoginView(Model model, String uri){
        logger.debug("현재 로그인 상태 아님");
        model.addAttribute("isLogin", false);
        return uri;
    }


    public static String makeLoginView(Model model, String uri, User loginUser) {
        logger.debug("현재 로그인 상태임");
        model.addAttribute("userName", loginUser.getName());
        model.addAttribute("isLogin", true);

        return uri;
    }

    public static String service(Model model, String uri, HttpSession session) {
        // 로그인 안되어 있음
        if(session == null) {
            return HtmlService.makeNotLoginView(model, uri);
        }
        // 로그인 되어 있음
        return HtmlService.makeLoginView(model, uri, (User)session.getAttribute("user"));
    }
}
