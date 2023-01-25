package service;

import db.UserRepository;
import exception.LogInFailedException;
import model.User;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("새로운 유저 DB에 정상적으로 추가되는지 테스트")
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
    @DisplayName("유저 검증 성공 테스트")
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
    @DisplayName("유저 검증 실패 테스트")
    public void testValidateUser_failed() {
        assertThatThrownBy(() -> userService.validateUser("user2", "password1"))
                .isInstanceOf(LogInFailedException.class)
                .hasMessage("로그인 실패");
    }

    @Test
    @DisplayName("유저리스트 정상적으로 반환하는지 테스트")
    public void testGetUserList() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "user1");
        parameters.put("password", "password1");
        parameters.put("name", "name1");
        parameters.put("email", "email1");

        userService.addUser(parameters);

        Collection<User> users = userService.getUserList();
        assertEquals(1, users.size());

        User user = users.iterator().next();
        assertEquals("user1", user.getUserId());
        assertEquals("password1", user.getPassword());
        assertEquals("name1", user.getName());
        assertEquals("email1", user.getEmail());
    }

    @Test
    @DisplayName("유저 이름 정상적으로 반환하는지 테스트")
    public void testGetUserName() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "user1");
        parameters.put("password", "password1");
        parameters.put("name", "name1");
        parameters.put("email", "email1");

        userService.addUser(parameters);
        assertEquals("name1", userService.getUserName(parameters.get("userId")));

    }

}
