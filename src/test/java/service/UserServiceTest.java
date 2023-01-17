package service;

import db.Database;
import exception.DuplicateUserIdException;
import model.Request.Request;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.UserService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    @AfterEach
    void clean() {
        Database.cleanAll();
    }

    @Test
    @DisplayName("유저 저장 (서비스 레벨)")
    void saveUser() throws IOException {
        //given
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        String requestMessage = "POST /user/create HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n\n"
                + body;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);

        //when
        UserService userService = new UserService();
        userService.signUpUser(request);

        //then
        User userById = Database.findUserById("javajigi");
        assertThat(userById.getEmail()).isEqualTo("javajigi@slipp.net");
    }

    @Test
    @DisplayName("중복 유저 회원가입시 exception 출력")
    void duplicateUserIdException() throws IOException {
        //given

        User user = new User("javajigi", "pwd", "tester", "test@test.com");
        Database.addUser(user);

        //when
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        String requestMessage = "POST /user/create HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: " + body.length() + "\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n\n"
                + body;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);
        UserService userService = new UserService();
        //then
        assertThrows(DuplicateUserIdException.class, () -> userService.signUpUser(request));

    }
}
