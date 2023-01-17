package controller;

import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAccountControllerTest {
    static UserAccountController userAccountController;

    @BeforeAll
    static void getUserAccountController() {
        userAccountController = UserAccountController.get();
    }

    @Test
    @DisplayName("유저 아이디 중복 검사")
    void 유저아이디_중복검사() {
        UserService.addUser(new User("asdf", "asdfasdf123", "asdf", "asdfasdf@asdf.com"));

        CustomHttpRequest req = CustomHttpRequest.of(
                "GET /user/create HTTP/1.1",
                Collections.EMPTY_LIST,
                List.of("userId=asdf&password=123123&email=asdfasdf@asdf.com")
        );

        CustomHttpResponse res = userAccountController.makeAccount(req);


        //then
        assertEquals(new String(res.getBody()), "userID duplicated");

    }
}