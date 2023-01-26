package com.example.demo.service;

import com.example.demo.db.Database;
import com.example.demo.model.User;

public class UserService {
    public static void join(User user) {
        Database.addUser(user);
    }
    public static void login(String userId, String password){

    }
}
