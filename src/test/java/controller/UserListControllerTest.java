package controller;

import db.SessionStorage;
import db.UserDAO;
import model.User;
import model.request.Request;
import model.request.UserCreate;
import model.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

public class UserListControllerTest {

    UserDAO userDAO = new UserDAO();

    @BeforeEach
    void cleanDb() throws SQLException {
        userDAO.deleteAll();
        SessionStorage.cleanAll();
    }

    @Test
    @DisplayName("유저 리스트 표시 기능")
    void userList() throws Exception {
        //given
        UserCreate user = new UserCreate("aa", "bb", "cc", "test@test");
        userDAO.insert(user);

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
