package service;

import db.UserDatabase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.error.erroclass.FailLoggedException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService = new UserService();
    private UserDatabase userDatabase = new UserDatabase();

    final String[] userValue = {"test", "1234", "testName", "test@naver.com"};
    final String[] userKey = {"userId", "password", "name", "email"};
    final String[] notCorrectId = {"ttttt", "1234"};
    final String[] notCorrectPassword = {"test", "12333"};
    final String[] validLogin = {"test", "1234"};

    @Test
    @DisplayName("로그인 성공 테스트")
    void validLogin() throws FailLoggedException {
        //given
        User user = new User(userValue[0], userValue[1], userValue[2], userValue[3]);
        userDatabase.addData(user);
        //when
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put(userKey[0], validLogin[0]);
        loginInfo.put(userKey[1], validLogin[1]);
        //then
        assertDoesNotThrow(()-> userService.validLogin(loginInfo));
    }

    @Test
    @DisplayName("로그인 아이디 불일치 테스트")
    void notValidIdLogin() {
        //given
        User user = new User(userValue[0], userValue[1], userValue[2], userValue[3]);
        userDatabase.addData(user);
        //when
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put(userKey[0], notCorrectId[0]);
        loginInfo.put(userKey[1], notCorrectId[1]);
        //then
        assertThrows(FailLoggedException.class,() -> userService.validLogin(loginInfo));
    }

    @Test
    @DisplayName("로그인 비밀번호 불일치 테스트")
    void notValidPasswordLogin() {
        //given
        User user = new User(userValue[0], userValue[1], userValue[2], userValue[3]);
        userDatabase.addData(user);
        //when
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put(userKey[0], notCorrectPassword[0]);
        loginInfo.put(userKey[1], notCorrectPassword[1]);
        //then
        assertThrows(FailLoggedException.class,() -> userService.validLogin(loginInfo));
    }
}