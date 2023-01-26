package controller;

import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.UserRepo;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAccountControllerTest {
    static UserAccountController userAccountController;

    @BeforeAll
    static void getUserAccountController() {
        userAccountController = UserAccountController.get();
        UserRepo.getInstance().addUser(new User("asdf", "asdfasdf123", "asdf", "asdfasdf@asdf.com"));
    }

    @AfterAll
    static void deleteUserAccount() {
        UserRepo.getInstance().delete("asdf");
    }

    @Test
    @DisplayName("유저 아이디 중복 검사")
    void 유저아이디_중복검사() {
        //given

        CustomHttpRequest req = CustomHttpRequest.of(
                "POST /user/create HTTP/1.1",
                Collections.EMPTY_LIST,
                List.of("userId=asdf&password=123a123&email=asdfasdf@asdf.com&name=hello")
        );

        //when
        CustomHttpResponse res = userAccountController.makeAccount(req);

        //then
        assertEquals(new String(res.getBody()), "userID duplicated");
    }

    @Test
    @DisplayName("유저 로그인 성공")
    void 유저_로그인_요청_성공() {
        //given
        CustomHttpRequest req = CustomHttpRequest.of(
                "POST /user/login HTTP/1.1",
                Collections.EMPTY_LIST,
                List.of("userId=asdf&password=asdfasdf123")
        );

        //when
        CustomHttpResponse res = userAccountController.loginAccount(req);

        //then
        assertEquals(res.getStatusLine("HTTP/1.1"), "HTTP/1.1 302 Found");
        assertEquals(res.getContentTypeLine(), "Content-Type: text/plain; charset=UTF-8");
    }


}