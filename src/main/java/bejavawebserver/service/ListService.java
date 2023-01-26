package bejavawebserver.service;

import bejavawebserver.model.User;
import bejavawebserver.repository.memoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ListService {
    public static String makeUserList(Model model, String uri, User loginUser){
        List<User> userList = new ArrayList<>(memoryRepository.findAll());
        model.addAttribute("userList", userList);
        return HtmlService.makeLoginView(model, uri, loginUser);
    }
}
