package service;

import db.Database;
import model.User;

import java.util.Map;

public class UserServiceImpl implements UserService {
    private final Database database;

    public UserServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public void join(User user) {
        database.addUser(user);
    }

    @Override
    public User findUser(String userId) {
        return database.findUserById(userId);
    }

    @Override
    public void login(String userId, String password) {
        User user = findUser(userId);
        if (user == null)
            throw new IllegalArgumentException("존재하지 않는 아이디입니다");
        if (!user.getPassword().equals(password))
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
    }

    @Override
    public void createUser(Map<String, String> params) {
        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );
        join(user);
    }
}
