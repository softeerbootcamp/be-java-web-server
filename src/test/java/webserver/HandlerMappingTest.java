package webserver;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import webserver.HandlerMapping.ControllerType;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import static org.mockito.Mockito.mock;

public class HandlerMappingTest {

    HandlerMapping handlerMapping;

    Request req;

    @BeforeEach
    void testSetUp() {
        handlerMapping = new HandlerMapping();
    }

    @Test
    @DisplayName("존재하는 컨트롤러를 호출했을 때")
    public void getHandlerTest_callValidController() throws HttpRequestException {
        //given
        String path = "/user/create";
        //when
        ControllerType controllerType = ControllerType.findController(path);
        //then
        Assertions.assertEquals(controllerType, ControllerType.USER);
    }

    @Test
    @DisplayName("스태틱 컨트롤러를 호출했을 때")
    public void getHandlerTest_callStaticController() throws HttpRequestException {
        //given
        String path = "/other/create";
        //when
        ControllerType controllerType = ControllerType.findController(path);
        //then
        Assertions.assertEquals(controllerType, ControllerType.STATIC);
    }

    @Test
    @DisplayName("존재하지 않은 컨트롤러를 호출했을 때")
    public void getHandlerTest_inValidController() throws HttpRequestException {
        //given
        String path = "lorem";
        //then
        Assertions.assertThrows(HttpRequestException.class, () -> ControllerType.findController(path));
    }
}
