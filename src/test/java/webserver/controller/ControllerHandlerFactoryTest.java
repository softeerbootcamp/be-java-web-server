package webserver.controller;

import enums.HttpMethod;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import webserver.domain.HttpRequest;
import webserver.domain.RequestLine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControllerHandlerFactoryTest {

    @Test
    void 쿼리스트링_핸들러반환_테스트() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Request Line", "GET user/create?name=박원종 HTTP/1.1");
        headers.put("HOST", "www.test01.com");


        ControllerHandler controllerHandler = ControllerHandlerFactory.getHandler(HttpRequest.newInstance(headers));
        Assertions.assertThat(controllerHandler).isInstanceOf(QueryStringHandler.class);
    }
}