package service;

import db.Database;
import dto.LogInDTO;
import dto.SignUpDTO;
import http.common.Session;
import model.User;

public class UserService {
    public String signUp(SignUpDTO signUpUserInfo) {
        User findUser = Database.findUserById(signUpUserInfo.getUserId());

        if (findUser != null) {
            return null;
        }

        User user = new User(signUpUserInfo.getUserId(), signUpUserInfo.getPassword(), signUpUserInfo.getName(), signUpUserInfo.getEmail());
        Database.addUser(user);

        return user.getUserId();
    }

    public Session logIn(LogInDTO logInUserInfo) {
        User findUser = Database.findUserById(logInUserInfo.getUserId());
        if (findUser != null && passwordMatch(findUser, logInUserInfo)) {
            Session session = new Session(findUser);
            Database.addSession(session);
            return session;
        }
        return null;
    }

    private boolean passwordMatch(User findUser, LogInDTO logInUserInfo) {
        return findUser.getPassword().equals(logInUserInfo.getPassword());
    }
}
