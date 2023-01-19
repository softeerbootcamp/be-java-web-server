package controller;

import db.Database;
import exception.UserNotFoundException;
import model.User;
import model.request.Request;
import model.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.UserCreateController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static model.response.HttpStatusCode.FOUND;
import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateControllerTest {
    @Test
    @DisplayName("유저 회원가입 테스트")
    void signUp() throws Exception {
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

        UserCreateController userCreateController = new UserCreateController();
        Response response = userCreateController.service(request);

        //expected
        Assertions.assertThat(response.getStatusLine().getHttpStatusCode()).isEqualTo(FOUND);

        User userById = Database.findUserById("javajigi").orElseThrow(UserNotFoundException::new);
        assertThat(userById.getEmail()).isEqualTo("javajigi@slipp.net");

    }
}
