package controller;

import Controller.Controller;
import Request.*;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import Controller.FileController;
import java.util.HashMap;

public class FileControllerTest {

    @Test
    void NonExistFileTest(){
        //given
        HttpRequest httpRequest = new HttpRequest(new HttpRequestStartLine(HttpMethod.GET.toString(), "/Iamgroot.html", "HTTP/1.1"),
                new HttpRequestParams(new HashMap<>()),
                new HttpRequestHeaders(new HashMap<>(){{
                    put("Host","localhost:8080");
                    put("Connection", "keep-alive");
                }}));
        //when
        Controller controller = new FileController();
        HttpResponse response = controller.createResponse(httpRequest);
        //then
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.NOT_FOUND, "HTTP/1.1"))
                .body(new HttpResponseBody("404: 존재하지않는 파일입니다.".getBytes()))
                .headers(new HttpResponseHeaders("", "404: 존재하지않는 파일입니다.".getBytes().length));
        Assertions.assertThat(response.getBody()).isEqualTo(httpResponse.getBody());

    }
}
