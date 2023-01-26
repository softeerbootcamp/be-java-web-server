package service;

import db.Database;
import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    User user;
    LoginService loginService;

    @BeforeEach
    void setUp() {
        user= User.of("javajigi","password","박재성","javajigi@slipp.net");
        loginService= LoginService.getInstance();
    }

    @Test
    @DisplayName("회원 가입 후 정상적인 로그인하는 경우")
    void loginAfterSignUp() {
        //given - 회원 가입이 진행됨
        Database.addUser(user);
        //when
        boolean res = loginService.checkRightUser("javajigi","password");
        //then
        assertThat(res).isTrue();
    }

    @Test
    @DisplayName("회원 가입 후 잘못된 로그인하는 경우  - 유저 아이디가 없으면 nullpointerexception, 패스워드가 불일치면 false")
    void wrongLoginAfterSignUp() {
        SoftAssertions softAssertions = new SoftAssertions();
        //given - 회원 가입이 진행됨
        Database.addUser(user);
        //when
        boolean wrongPassword = loginService.checkRightUser("javajigi","passwfdyord");
        //then
        // assertJ에서의 예외 처리 방법
        softAssertions.assertThatThrownBy(() -> loginService.checkRightUser("javawqwqjigi","password")).isInstanceOf(NullPointerException.class);
        softAssertions.assertThat(wrongPassword).isFalse();
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("회원 가입 하지 않고 로그인하는 경우")
    void loginNotAfterSignUp() {
        //given
        //when
        //then
        assertThatThrownBy(() -> loginService.checkRightUser("empty","empty")).isInstanceOf(NullPointerException.class);

    }
}
