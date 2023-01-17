package webserver;

import org.junit.jupiter.api.*;
import webserver.controller.Controller;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import static org.mockito.Mockito.mock;

public class HandlerMappingTest {

    HandlerMapping handlerMapping;

    @BeforeEach
    void testSetUp() {
        handlerMapping = new HandlerMapping();
    }

    @Test
    @DisplayName("존재하는 컨트롤러를 호출했을 때")
    public void getHandlerTest_callValidController() throws HttpRequestException {
        //given
        Request req = Request.of("GET /user/create HTTP/1.1", "", "");
        //when
        Controller controllerType = handlerMapping.getHandler(req);
        //then
        Assertions.assertEquals(controllerType, HandlerMapping.controllerMap.get("/user"));
    }

    @Test
    @DisplayName("존재하지 않은 컨트롤러를 호출했을 때")
    public void getHandlerTest_inValidController() throws HttpRequestException {
        //given
        Request req = Request.of("GET /user/create HTTP/1.1", "", "");
        //then
        Assertions.assertThrows(HttpRequestException.class, () -> handlerMapping.getHandler(req));
    }
}
