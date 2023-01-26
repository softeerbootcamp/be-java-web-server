package bejavawebserver.service;

import bejavawebserver.model.QnaForm;
import bejavawebserver.repository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class QnaService {
    @Autowired JdbcRepository jdbcRepository;
    public void addQna(QnaForm qnaForm) {
        jdbcRepository.addQna(qnaForm);
    }

    public void makeQnaList(Model model){
        List<QnaForm> qnaList = jdbcRepository.findQnaAll();
        model.addAttribute("qnaList", qnaList);
    }
}
