package service;

import model.User;

import javax.naming.AuthenticationException;
import java.util.Map;

public interface UserService {
    void join(User user);

    User findUser(String userId);

    void login(String userId, String password) throws AuthenticationException;

    void createUser(Map<String, String> params);
}
