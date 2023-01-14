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
        String path = "/user/create";
        Request req = mock(Request.class);
        //when
        Controller controllerType = handlerMapping.getHandler(req);
        //then
        Assertions.assertEquals(HandlerMapping.controllerMap.get("/user"), controllerType);
    }

    @Test
    @DisplayName("존재하지 않은 컨트롤러를 호출했을 때")
    public void getHandlerTest_inValidController() throws HttpRequestException {
        //given
        String path = "/lorem";
        Request req = mock(Request.class);

        //then
        Assertions.assertThrows(HttpRequestException.class, () -> handlerMapping.getHandler(req));
    }
}
