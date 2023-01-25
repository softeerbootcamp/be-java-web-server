package bejavawebserver.service;

import bejavawebserver.repository.memoryRepository;
import bejavawebserver.model.User;

import javax.servlet.http.HttpSession;

public class LoginService {
    public static boolean loginCheck(User user, HttpSession session){
        return memoryRepository.findUserById(user.getUserId()) != null;
    }
}
