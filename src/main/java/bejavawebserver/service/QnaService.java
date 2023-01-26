package bejavawebserver.service;

import bejavawebserver.model.QnaForm;
import bejavawebserver.repository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QnaService {
    @Autowired JdbcRepository jdbcRepository;
    public void addQna(QnaForm qnaForm) {
        jdbcRepository.addQna(qnaForm);
    }
}
