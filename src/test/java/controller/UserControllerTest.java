package controller;

import db.Database;
import http.common.Body;
import http.common.Header;
import http.request.HttpRequest;
import http.request.RequestStartLine;
import http.response.HttpResponse;
import http.response.ResponseFactory;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {

    UserController userController = new UserController();

    @Test
    @DisplayName("회원 가입 성공")
    void signUp_success() {
        // given
        HttpRequest httpRequest = getSignUpRequest();
        HttpResponse httpResponse = new ResponseFactory().create(new ByteArrayOutputStream());
        // when
        userController.service(httpRequest, httpResponse);
        // then - check how response changed
        assertThat(Database.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("회원 가입 실패 - 중복 아이디")
    void signUp_fail_duplicated_id() {
        // given
        Database.addUser(getUser());
        HttpRequest httpRequest = getSignUpRequest();
        HttpResponse httpResponse = new ResponseFactory().create(new ByteArrayOutputStream());
        // when
        userController.service(httpRequest, httpResponse);
        // then
        assertThat(Database.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("로그인 성공")
    void signIn_success() {
        // given
        Database.addUser(getUser());
        HttpRequest httpRequest = getLogInRequest();
        HttpResponse httpResponse = new ResponseFactory().create(new ByteArrayOutputStream());
        // when
        userController.service(httpRequest, httpResponse);
        // then - test what?
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 아이디")
    void signIn_fail_user_not_found() {
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 틀림")
    void signIn_fail_password_not_match() {
    }

    private HttpRequest getLogInRequest() {
        String logInBodyMsg = getLogInBodyMsg();
        return new HttpRequest(
                new RequestStartLine(getLogInRequestStartLine()),
                new Header(contentLength(logInBodyMsg)),
                new Body(logInBodyMsg)
        );
    }

    private String contentLength(String msg) {
        return "Content-length=" + msg.length();
    }

    private String getLogInBodyMsg() {
        User user = getUser();
        return "userId=" + user.getUserId() + "&password=" + user.getPassword();

    }

    private String getLogInRequestStartLine() {
        return "POST /user/login HTTP/1.1";
    }

    private HttpRequest getSignUpRequest() {
        String signInBodyMsg = getSignInBodyMsg();
        return new HttpRequest(
                new RequestStartLine(getSignUpRequestStartLine()),
                new Header(contentLength(signInBodyMsg)),
                new Body(signInBodyMsg)
        );
    }

    private String getSignInBodyMsg() {
        User user = getUser();
        return "userId=" + user.getUserId() + "&password=" + user.getPassword() + "&name=" + user.getName() + "&email=" + user.getEmail();
    }

    private User getUser() {
        return new User("a", "b", "C", "D@E.com");
    }

    private String getSignUpRequestStartLine() {
        return "POST /user/create HTTP/1.1";
    }

}