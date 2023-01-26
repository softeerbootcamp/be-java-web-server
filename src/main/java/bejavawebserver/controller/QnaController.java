package bejavawebserver.controller;

import bejavawebserver.model.LoginForm;
import bejavawebserver.model.QnaForm;
import bejavawebserver.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class QnaController {
    @Autowired QnaService qnaService;
    @PostMapping("/qna/form")
    public String writeQna(QnaForm qnaForm){
        qnaService.addQna(qnaForm);
        return "redirect:/index.html";
    }

}
