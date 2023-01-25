package bejavawebserver.service;

import bejavawebserver.repository.memoryDB;
import bejavawebserver.model.User;

import javax.servlet.http.HttpSession;

public class LoginService {
    public static boolean loginCheck(User user, HttpSession session){
        return memoryDB.findUserById(user.getUserId()) != null;
    }
}
