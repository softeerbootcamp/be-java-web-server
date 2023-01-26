package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class QnaController {

    @GetMapping("/form")
    public String boardGernerate() {
        return "qna/form";
    }

    @GetMapping("/show")
    public String boardShow() {
        return "qna/show";
    }
}
