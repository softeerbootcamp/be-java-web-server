package bejavawebserver.service;

import bejavawebserver.db.memoryDB;
import bejavawebserver.model.User;

public class SignUpService {
    public static void addDatabase(User user) {
        //if(memoryDB.checkDuplicate(user)) throw new DuplicateSignUpUserException("중복된 사용자가 있습니다.");
        memoryDB.addUser(user);
    }
}
