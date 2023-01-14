package webserver.controller;
import org.junit.jupiter.api.*;
import webserver.Service.AuthService;
import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class AuthControllerTest {

    AuthController authController;

    @BeforeEach
    void testSetUp(){
        authController = new AuthController();
    }

    @Test
    @DisplayName("회원가입 메소드 테스트")
    public void userCreateTest() throws HttpRequestException {

        AuthService authService = mock(AuthService.class);
        Response response = mock(Response.class);

        // given
        Map<String, String> queryStrs = new HashMap<>();
        queryStrs.put("userId", "testUser");
        queryStrs.put("password", "testPass");
        queryStrs.put("name", "testName");
        queryStrs.put("email", "test@email.com");
        byte[] joinResult = "testResult".getBytes();

        // when
        authController.userCreate(queryStrs, response);
        when(authService.join("testUser", "testPass", "testName", "test@email.com")).thenReturn(joinResult);

        //then
        verify(authService, times(1)).join("testUser", "testPass", "testName", "test@email.com");
        verify(response).redirect(StatusCodes.SEE_OTHER, joinResult, ContentType.TEXT_HTML, "http://localhost:8080/index.html");
    }

    @Test
    @DisplayName("체이닝 메소드 검사")
    void chainingMethodTest_ValidPath(){

        //given
        String path = "/user/create";
        Response res = mock(Response.class);
        Map<String, String> queryStrs = new HashMap<>();
        queryStrs.put("userId", "testUser");
        queryStrs.put("password", "testPass");
        queryStrs.put("name", "testName");
        queryStrs.put("email", "test@email.com");

        authController.chain(path, queryStrs, res);

        //then
        verify(authController, times(1)).userCreate(queryStrs, res);
    }


    @Test
    @DisplayName("체이닝 메소드 검사-옳지 않은 경로로 보냈을 때")
    void chainingMethodTest_inValidPath(){

        //given
        String path = "/user/***";
        Response res = mock(Response.class);
        Map<String, String> queryStrs = new HashMap<>();
        queryStrs.put("userId", "testUser");
        queryStrs.put("password", "testPass");
        queryStrs.put("name", "testName");
        queryStrs.put("email", "test@email.com");

        //then
        Assertions.assertThrows(HttpRequestException.class, () -> authController.chain(path, queryStrs, res));

    }


    @Test
    @DisplayName("체이닝 메소드 검사_올바르지 않은 파라미터를 보넀을 때")
    void chainingMethodTest_inValidParameter(){

        //given
        String path = "/user/***";
        Response res = mock(Response.class);
        Map<String, String> queryStrs = new HashMap<>();
        queryStrs.put("password", "testPass");
        queryStrs.put("name", "testName");
        queryStrs.put("email", "test@email.com");

        //when

        //then
        Assertions.assertThrows(HttpRequestException.class, () -> authController.chain(path, queryStrs, res));
    }



}