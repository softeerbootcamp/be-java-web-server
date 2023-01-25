package service;

import db.user.UserDatabase;
import model.User;

import javax.naming.AuthenticationException;
import java.util.Collection;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private static UserService userService;
    private final UserDatabase userDatabase;

    private UserServiceImpl(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public static UserService getInstance(UserDatabase userDatabase) {
        if (userService == null) userService = new UserServiceImpl(userDatabase);
        return userService;
    }

    @Override
    public void join(User user) {
        userDatabase.addUser(user);
    }

    @Override
    public User findUser(String userId) {
        return userDatabase.findUserById(userId);
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
        return userDatabase.findAll();
    }
}
