package bejavawebserver.service;

import bejavawebserver.model.User;
import bejavawebserver.repository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ListService {
    @Autowired JdbcRepository jdbcRepository;

    public void makeUserList(Model model){
        List<User> userList = jdbcRepository.findUserAll();
        model.addAttribute("userList", userList);
    }
}
