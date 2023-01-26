package bejavawebserver.controller;

import bejavawebserver.model.Qna;
import bejavawebserver.model.QnaForm;
import bejavawebserver.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class QnaController {
    @Autowired
    QnaService qnaService;

    @PostMapping("/qna/form")
    public String writeQna(QnaForm qnaForm) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        qnaService.addQna(new Qna(qnaForm, time));
        return "redirect:/index.html";
    }

}
