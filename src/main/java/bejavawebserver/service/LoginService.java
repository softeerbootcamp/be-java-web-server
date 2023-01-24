package bejavawebserver.service;

import bejavawebserver.db.memoryDB;
import bejavawebserver.model.User;

import javax.servlet.http.HttpSession;

public class LoginService {
    public static boolean loginCheck(User user, HttpSession session){
        return memoryDB.findUserById(user.getUserId()) != null;
    }
}
