package service;

import db.Database;
import model.User;

import javax.naming.AuthenticationException;
import java.util.Collection;
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
    public void login(String userId, String password) throws AuthenticationException {
        User user = findUser(userId);
        if (user == null)
            throw new AuthenticationException("존재하지 않는 아이디입니다");
        if (!user.getPassword().equals(password))
            throw new AuthenticationException("잘못된 비밀번호입니다.");
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

    @Override
    public Collection<User> findAllUsers() {
        return database.findAll();
    }
}
