package bejavawebserver.service;

import bejavawebserver.model.User;
import bejavawebserver.repository.JdbcRepository;
import bejavawebserver.repository.MemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListService {
    @Autowired JdbcRepository jdbcRepository;

    public String makeUserList(Model model, String uri, User loginUser){
        List<User> userList = new ArrayList<>(jdbcRepository.findAll());
        model.addAttribute("userList", userList);
        return HtmlService.makeLoginView(model, uri, loginUser);
    }
}
