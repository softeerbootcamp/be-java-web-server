package service;

import db.Database;
import exception.DuplicateUserIdException;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    @Test
    @DisplayName("유저 저장 (서비스 레벨)")
    void saveUser() {
        //given
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("userId", "testId");
        requestParams.put("password", "testPassword");
        requestParams.put("name", "tester");
        requestParams.put("email", "test@test.com");
        //when
        UserService userService = new UserService();
        userService.signUpUser(requestParams);

        //then
        User userById = Database.findUserById("testId");
        Assertions.assertThat(userById.getName()).isEqualTo("tester");
    }

    @Test
    @DisplayName("중복 유저 회원가입시 exception 출력")
    void duplicateUserIdException() {
        //given
        User user = new User("1", "pwd", "tester", "test@test.com");
        Database.addUser(user);

        //when
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("userId", "1");
        requestParams.put("password", "testPassword");
        requestParams.put("name", "tester");
        requestParams.put("email", "test@test.com");
        UserService userService = new UserService();

        //then
        assertThrows(DuplicateUserIdException.class, () -> userService.signUpUser(requestParams));

    }
}
