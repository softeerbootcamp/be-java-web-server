package bejavawebserver.service;

import bejavawebserver.repository.JdbcRepository;
import bejavawebserver.repository.MemoryRepository;
import bejavawebserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class SignUpService {
    @Autowired JdbcRepository jdbcRepository;
    public void addDatabase(User user) {
        if(jdbcRepository.checkDuplicate(user)) throw new RuntimeException("중복된 사용자가 있습니다.");
        jdbcRepository.addUser(user);
    }
}
