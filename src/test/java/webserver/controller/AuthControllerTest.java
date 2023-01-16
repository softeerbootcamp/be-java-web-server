package webserver.controller;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import webserver.ControllerExecutioner;
import webserver.Service.AuthService;
import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(authService.join(anyString(), anyString(), anyString(), anyString())).thenReturn(joinResult);

        //then
        authController.userCreate(queryStrs, response);
        verify(response).redirect(StatusCodes.SEE_OTHER, joinResult, ContentType.TEXT_HTML, "http://localhost:8080/index.html");
    }

    @Test
    @DisplayName("체이닝 메소드 검사-올바른 경로로 보냈을 떄")
    void chainingMethodTest_ValidPath(){

        //given
        String path = "/user/create";
        Response res = mock(Response.class);
        AuthService authService = mock(AuthService.class);

        Map<String, String> queryStrs = new HashMap<>();
        MockedStatic<ControllerExecutioner> ceMock = mockStatic(ControllerExecutioner.class);

        queryStrs.put("userId", "testUser");
        queryStrs.put("password", "testPass");
        queryStrs.put("name", "testName");
        queryStrs.put("email", "test@email.com");

        ceMock.when(()-> ControllerExecutioner.executeController(Mockito.any(Class.class), Mockito.any(Map.class), Mockito.any(Response.class), Mockito.any(String.class))).thenAnswer(invocation->null);
        authController.chain(path, queryStrs, res);

        //then
        verify(authService, times(1)).join(anyString(), anyString(), anyString(), anyString());
    }


    @Test
    @DisplayName("체이닝 메소드 검사-옳지 않은 경로로 보냈을 때")
    void chainingMethodTest_inValidPath(){

        //given
        String path = "/user/***";
        Response res = mock(Response.class);
        MockedStatic<ControllerExecutioner> ceMock = mockStatic(ControllerExecutioner.class);

        Map<String, String> queryStrs = new HashMap<>();
        queryStrs.put("userId", "testUser");
        queryStrs.put("password", "testPass");
        queryStrs.put("name", "testName");
        queryStrs.put("email", "test@email.com");

        //when
        ceMock.when(()-> ControllerExecutioner.executeController(Mockito.any(Class.class), Mockito.any(Map.class), Mockito.any(Response.class), Mockito.any(String.class))).thenAnswer(invocation->null);
        authController.chain(path, queryStrs, res);

        //then
        Assertions.assertThrows(HttpRequestException.class, () -> authController.chain(path, queryStrs, res));

    }


    @Test
    @DisplayName("체이닝 메소드 검사_올바르지 않은 파라미터를 보넀을 때")
    void chainingMethodTest_inValidParameter(){

        //given
        String path = "/user/***";
        Response res = mock(Response.class);
        MockedStatic<ControllerExecutioner> ceMock = mockStatic(ControllerExecutioner.class);

        Map<String, String> queryStrs = new HashMap<>();
        queryStrs.put("password", "testPass");
        queryStrs.put("name", "testName");
        queryStrs.put("email", "test@email.com");

        //when
        ceMock.when(()-> ControllerExecutioner.executeController(Mockito.any(Class.class), Mockito.any(Map.class), Mockito.any(Response.class), Mockito.any(String.class))).thenThrow(new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR));
        authController.chain(path, queryStrs, res);

        //then
        assertEquals(StatusCodes.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }



}