package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.model.BoardDto;
import com.example.demo.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/qna")
public class QnaController {

    @GetMapping("/form")
    public String boardGernerate(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "qna/form";
    }

    @GetMapping("/show")
    public String boardShow(Model model) {
        return "qna/show";
    }

    @PostMapping("/boardGenerate.do")
    public String boardGenerateExcute(@ModelAttribute("boardDto") BoardDto boardDto) {
        Board board = new Board(boardDto.getWriter(), boardDto.getTitle(), boardDto.getContent());

        log.debug("writer:{}", boardDto.getWriter());
        log.debug("title:{}", boardDto.getTitle());
        log.debug("content:{}", boardDto.getContent());

        BoardService.save(board);

        String writer = BoardService.printOne(board.getWriter());

        log.debug("저장된 작성자 : {}",writer);

        return "redirect:/index";
    }
}
