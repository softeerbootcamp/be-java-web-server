package webserver.controller;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import webserver.ControllerInterceptor;
import webserver.Service.AuthService;
import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    AuthController authController;
    AuthService authService;
    Request request;
    Response response;
    MockedStatic<ControllerInterceptor> ceMock;

    @BeforeEach
    void testSetUp(){
        authController = new AuthController();
        String requestLine = "GET /index.html /1.1";
        String header = "password: testPass\n"+
                        "name: testName\n"+
                        "email : test@email.com";

        String body = "";
        request = Request.of(requestLine, header, body);
        response = mock(Response.class);
        authService = mock(AuthService.class);
        ceMock = mockStatic(ControllerInterceptor.class);

    }

    @Test
    @DisplayName("회원가입 메소드 테스트")
    public void userCreateTest() throws HttpRequestException {

        AuthService authService = mock(AuthService.class);

        // given
        byte[] joinResult = "testResult".getBytes();

        // when
        when(authService.join(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(joinResult);

        //then
        authController.userCreate(request.getRequestHeader(), response);
        verify(response).redirect(StatusCodes.SEE_OTHER, joinResult, ContentType.TEXT_HTML, "http://localhost:8080/index.html");
    }

    @Test
    @DisplayName("체이닝 메소드 검사-올바른 경로로 보냈을 떄")
    void chainingMethodTest_ValidPath() throws IOException {

        //given
        String path = "/user/create";
        AuthService authService = mock(AuthService.class);
        MockedStatic<ControllerInterceptor> ceMock = mockStatic(ControllerInterceptor.class);

        ceMock.when(()-> ControllerInterceptor.executeController(Mockito.any(Class.class),  Mockito.any(Request.class), Mockito.any(Response.class))).thenThrow(new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR));
        authController.chain(request, response);

        //then
        verify(authController, times(1)).userCreate(Mockito.any(Map.class), Mockito.any(Response.class));
    }


    @Test
    @DisplayName("체이닝 메소드 검사-옳지 않은 경로로 보냈을 때")
    void chainingMethodTest_inValidPath() throws IOException {

        //given
        String path = "/user/***";
        MockedStatic<ControllerInterceptor> ceMock = mockStatic(ControllerInterceptor.class);

        //when
        ceMock.when(()-> ControllerInterceptor.executeController(Mockito.any(Class.class),  Mockito.any(Request.class), Mockito.any(Response.class))).thenThrow(new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR));
        authController.chain(request, response);

        //then
        Assertions.assertThrows(HttpRequestException.class, () -> authController.chain(request, response));

    }


    @Test
    @DisplayName("체이닝 메소드 검사_올바르지 않은 파라미터를 보넀을 때")
    void chainingMethodTest_inValidParameter() throws IOException {

        //given
        String path = "/user/***";
        MockedStatic<ControllerInterceptor> ceMock = mockStatic(ControllerInterceptor.class);

        //when
        ceMock.when(()-> ControllerInterceptor.executeController(Mockito.any(Class.class),  Mockito.any(Request.class), Mockito.any(Response.class))).thenThrow(new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR));
        authController.chain(request, response);

        //then
        assertEquals(StatusCodes.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }



}