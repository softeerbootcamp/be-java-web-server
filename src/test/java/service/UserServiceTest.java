package service;

import db.UserRepository;
import exception.LogInFailedException;
import model.User;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

    @Test
    public void testValidateUser() throws LogInFailedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "user1");
        parameters.put("password", "password1");
        parameters.put("name", "name1");
        parameters.put("email", "email1");

        userService.addUser(parameters);
        userService.validateUser("user1", "password1");
    }

    @Test
    public void testValidateUser_failed() {
        assertThatThrownBy(() -> userService.validateUser("user2", "password1"))
                .isInstanceOf(LogInFailedException.class)
                .hasMessage("로그인 실패");
    }
}
