package bejavawebserver.service;

import bejavawebserver.controller.HtmlController;
import bejavawebserver.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class HtmlService {
    private static final Logger logger = LoggerFactory.getLogger(HtmlController.class);

    public String makeNotLoginView(Model model, String uri) {
        logger.debug("현재 로그인 상태 아님");
        model.addAttribute("isLogin", false);
        return uri;
    }

    public String makeLoginView(Model model, String uri, User loginUser) {
        logger.debug("현재 로그인 상태임");
        model.addAttribute("userName", loginUser.getName());
        model.addAttribute("isLogin", true);

        return uri;
    }
}
