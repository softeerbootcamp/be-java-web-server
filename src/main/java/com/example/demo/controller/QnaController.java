package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.model.BoardDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class QnaController {

    @GetMapping("/form")
    public String boardGernerate() {
        return "qna/form";
    }

    @GetMapping("/show")
    public String boardShow(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "qna/show";
    }

    @PostMapping("/boardGenerate.do")
    public String boardGenerateExcute(@ModelAttribute("boardDto") BoardDto boardDto) {
        Board board = new Board(boardDto.getWriter(), boardDto.getTitle(), boardDto.getContent());

        return "redirect:/qna/show";
    }
}
