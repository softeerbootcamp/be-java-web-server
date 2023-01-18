package controller;

import db.Database;
import db.SessionStorage;
import model.User;
import model.request.Request;
import model.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class UserListControllerTest {

    @BeforeEach
    void cleanDb() {
        Database.cleanAll();
        SessionStorage.cleanAll();
    }

    @Test
    @DisplayName("유저 리스트 표시 기능")
    void userList() throws Exception {
        //given
        User user = new User("aa", "bb", "cc", "test@test");
        Database.addUser(user);

        //when
        String requestMessage = "GET /user/list.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n";
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);

        //when
        webserver.controller.UserListController userListController = new webserver.controller.UserListController();
        Response service = userListController.service(request);
        //then

    }
}
