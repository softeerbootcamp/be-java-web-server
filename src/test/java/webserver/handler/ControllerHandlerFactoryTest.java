package webserver.handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import webserver.domain.HttpRequest;
import webserver.domain.HttpRequestMessage;

import java.util.HashMap;
import java.util.Map;

class ControllerHandlerFactoryTest {

    @Test
    void 스태틱_핸들러반환_테스트() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Request Line", "GET /index.html HTTP/1.1");
        headers.put("HOST", "www.test01.com");


        ControllerHandler controllerHandler = ControllerHandlerFactory.getHandler(HttpRequest.newInstance(new HttpRequestMessage(headers, null)));
        Assertions.assertThat(controllerHandler).isInstanceOf(StaticHandler.class);
    }
}