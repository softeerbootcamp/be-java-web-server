package webserver.controller;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import webserver.Service.UserService;
import webserver.domain.ContentType;
import webserver.view.ModelAndView;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {
/*
    UserController userController;
    UserService userService;
    Request request;
    Response response;
    MockedStatic<ControllerInterceptor> ceMock;

    ModelAndView mv;

    @BeforeEach
    void testSetUp(){
        userController = UserController.getInstance();
        String requestLine = "GET /user/form.html HTTP/1.1";
        String header = "password: testPass\n"+
                        "name: testName\n"+
                        "email : test@email.com";

        String body = "";
        request = Request.of(requestLine, header, body);
        response = mock(Response.class);
        userService = mock(UserService.class);
        ceMock = mockStatic(ControllerInterceptor.class);
        mv = mock(ModelAndView.class);
    }

    @Test
    @DisplayName("회원가입 메소드 테스트")
    public void userCreateTest() throws HttpRequestException {

        UserService userService = mock(UserService.class);

        // given
        byte[] joinResult = "testResult".getBytes();

        // when
        when(userService.addUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(joinResult);

        //then
        userController.userCreate(request.getRequestHeader(), response, mv);
        verify(response).redirect(StatusCodes.SEE_OTHER, joinResult, ContentType.TEXT_HTML, "http://localhost:8080/index.html");
    }

    @Test
    @DisplayName("체이닝 메소드 검사-올바른 경로로 보냈을 떄")
    void chainingMethodTest_ValidPath() throws IOException {

        //given
        String path = "/user/create";
        UserService userService = mock(UserService.class);
        MockedStatic<ControllerInterceptor> ceMock = mockStatic(ControllerInterceptor.class);

        userController.chain(request, response, mv);

        //then
        verify(userController, times(1)).userCreate(Mockito.any(Map.class), Mockito.any(Response.class), mv);
    }


    @Test
    @DisplayName("체이닝 메소드 검사-옳지 않은 경로로 보냈을 때")
    void chainingMethodTest_inValidPath() throws IOException {

        //given
        String path = "/user/***";
        MockedStatic<ControllerInterceptor> ceMock = mockStatic(ControllerInterceptor.class);

        //when
        ceMock.when(()-> ControllerInterceptor.executeController(Mockito.any(UserController.class),  Mockito.any(Request.class), Mockito.any(Response.class), mv)).thenThrow(new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR));
        userController.chain(request, response, mv);

        //then
        Assertions.assertThrows(HttpRequestException.class, () -> userController.chain(request, response, mv));

    }


    @Test
    @DisplayName("체이닝 메소드 검사_올바르지 않은 파라미터를 보넀을 때")
    void chainingMethodTest_inValidParameter() throws IOException {

        //given
        String path = "/user/***";
        MockedStatic<ControllerInterceptor> ceMock = mockStatic(ControllerInterceptor.class);

        //when
        ceMock.when(()-> ControllerInterceptor.executeController(Mockito.any(UserController.class),  Mockito.any(Request.class), Mockito.any(Response.class), mv)).thenThrow(new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR));
        userController.chain(request, response, mv);

        //then
        assertEquals(StatusCodes.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

*/

}