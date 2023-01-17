package service;

import db.Database;
import dto.LogInDTO;
import dto.SignUpDTO;
import model.User;

public class UserService {
    public String signUp(SignUpDTO userInfo) {
        User findUser = Database.findUserById(userInfo.getUserId());

        if (findUser != null) {
            return null;
        }

        User user = new User(userInfo.getUserId(), userInfo.getPassword(), userInfo.getName(), userInfo.getEmail());
        Database.addUser(user);

        return user.getUserId();
    }

    public Session logIn(LogInDTO userInfo) {
        User findUser = Database.findUserById(userInfo.getUserId());

        if (findUser != null) {
            return new Session(findUser);
        }

        return new Session(null); // todo: throw 적용하기
    }
}
