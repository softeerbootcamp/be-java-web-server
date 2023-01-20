package service;

import db.UserRepository;
import model.User;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {

    private final UserRepository userRepository = new UserRepository();
    private final UserService userService = new UserService(userRepository);


    @Test
    public void testAddUser() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "user1");
        parameters.put("password", "password1");
        parameters.put("name", "name1");
        parameters.put("email", "email1");

        userService.addUser(parameters);

        Collection<User> users = userService.getUserList();
        assertEquals(1, users.size());
    }

}
