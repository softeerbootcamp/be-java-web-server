package service;

import db.Database;
import enums.HttpMethod;
import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;
import session.HttpSessionManager;

import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.invoke.MethodHandles.throwException;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SignUpServiceTest {

    HttpRequest getRequest;
    HttpRequest postRequest;

    SignUpService signUpService;

    @BeforeEach
    void setUp() {
        Map<String,String> dummyQuery=Map.of(
                "userId","javajigi",
                "password","password",
                "name","%EB%B0%95%EC%9E%AC%EC%84%B1",
                "email","javajigi%40slipp.net"
        );
        Map<String,String> dummyCookies=Map.of(
                "sid",UUID.randomUUID().toString()
        );

        Map<String,String> dummyHeaders=Map.of(
                "Content-Type","application/x-www-form-urlencoded"
        );

        getRequest = new HttpRequest(HttpMethod.GET,"/user/create",dummyQuery,dummyCookies,null,null);
        //http form을 통해 POST 요청이 들어옴
        postRequest = new HttpRequest(HttpMethod.POST,"/user/create",null,dummyCookies,dummyHeaders,"userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        signUpService = SignUpService.getInstance();
    }

    @Test
    @DisplayName("Get을 통한 회원가입 테스트")
    void signUp() throws UnsupportedEncodingException {

        SoftAssertions softAssertions = new SoftAssertions();

        //given
        HttpRequest request = this.getRequest;

        //when
        signUpService.signUp(request);

        //then
        softAssertions.assertThat(Database.findUserById("javajigi")).isNotNull();
        softAssertions.assertThat(Database.findUserById("javajigi").getEmail()).isEqualTo("javajigi@slipp.net");
        softAssertions.assertAll();

    }

    @Test
    @DisplayName("POST를 통한 회원 가입 테스트")
    void singUpByPost() throws UnsupportedEncodingException {
        SoftAssertions softAssertions = new SoftAssertions();
        //given
        HttpRequest request = this.postRequest;
        //when
        signUpService.singUpByPost(request);
        //then
        softAssertions.assertThat(Database.findUserById("javajigi")).isNotNull();
        softAssertions.assertThat(Database.findUserById("javajigi").getEmail()).isEqualTo("javajigi@slipp.net");
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("POST를 통한 요청은 맞지만,application/x-www-form-urlencoded은 아닌 요청에 대해 테스트")
    void singUpByPostButNotForm() throws UnsupportedEncodingException {
        SoftAssertions softAssertions = new SoftAssertions();
        Map<String,String> dummyCookies=Map.of(
                "sids",UUID.randomUUID().toString()
        );
        //given
        HttpRequest request = new HttpRequest(HttpMethod.POST,"/user/create",null,dummyCookies,null,"userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        //when , then
        // assertJ에서의 첫 번째 예외 처리 방법
        softAssertions.assertThatThrownBy(() -> signUpService.singUpByPost(request)).isInstanceOf(NullPointerException.class);
        // assertJ에서의 두 번째 예외 처리 방법 ( 유명? 자주 발생하는 예외에 대하여 - 노션에 설명 참고)
        assertThatNullPointerException().isThrownBy(() -> signUpService.singUpByPost(request));
        softAssertions.assertAll();
    };

}
