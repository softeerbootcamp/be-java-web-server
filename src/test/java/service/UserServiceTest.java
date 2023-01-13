package service;

import db.Database;
import exception.DuplicateUserIdException;
import model.Request;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    @AfterEach
    void clean() {
        Database.cleanAll();
    }

    @Test
    @DisplayName("유저 저장 (서비스 레벨)")
    void saveUser() {
        //given
        Request request = new Request("/user/create?userId=aa&password=bb&name=cc&email=dd@dd");
        request.checkUrlQueryString();
        //when
        UserService userService = new UserService();
        userService.signUpUser(request);

        //then
        User userById = Database.findUserById("aa");
        assertThat(userById.getName()).isEqualTo("cc");
        assertThat(userById.getEmail()).isEqualTo("dd@dd");
    }

    @Test
    @DisplayName("중복 유저 회원가입시 exception 출력")
    void duplicateUserIdException() {
        //given
        User user = new User("aa", "pwd", "tester", "test@test.com");
        Database.addUser(user);

        //when
        Request request = new Request("/user/create?userId=aa&password=bb&name=cc&email=dd@dd");
        request.checkUrlQueryString();
        UserService userService = new UserService();
        //then
        assertThrows(DuplicateUserIdException.class, () -> userService.signUpUser(request));

    }
}
