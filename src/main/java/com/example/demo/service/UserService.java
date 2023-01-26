package com.example.demo.service;

import com.example.demo.db.Database;
import com.example.demo.model.User;

public class UserService {
    public static void join(User user) {
        Database.addUser(user);
    }
    public static boolean login(String userId, String password){
        try {
            User user = Database.findUserById(userId);
            if (user.getPassword().equals(password)) {
                return true;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("회원이 없습니다");
        }
        return false;
    }
}
