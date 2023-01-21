package controller;

import Controller.Controller;
import Request.*;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import db.SessionDb;
import model.Session;
import model.User;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Controller.FileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;
import view.FileView;

import java.util.HashMap;

public class FileControllerTest {
    private Logger logger = LoggerFactory.getLogger(FileControllerTest.class);

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
    @Test
    @DisplayName("동적 html 생성을 테스트")
    void DynamicFileTest(){
        //given
        HttpRequest joinRequest = new HttpRequest(new HttpRequestStartLine(HttpMethod.POST.toString(), "/user/create", "HTTP/1.1"),
                new HttpRequestParams(HttpRequestUtil.extractParams("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net")),
                new HttpRequestHeaders(new HashMap<>(){{
                    put("Host","localhost:8080");
                    put("Connection", "keep-alive");
                    put("Content-Length", "59");
                    put("Content-Type", "application/x-www-form-urlencoded");
                    put("Accept", "*/*");
                }}));
        UserDbUtil.saveUser(joinRequest);

        String sessionId = SessionDb.saveNewSession(joinRequest);
        HttpRequest httpRequest = new HttpRequest(new HttpRequestStartLine(HttpMethod.GET.toString(), "/index.html", "HTTP/1.1"),
                new HttpRequestParams(new HashMap<>()),
                new HttpRequestHeaders(new HashMap<>(){{
                    put("Host","localhost:8080");
                    put("Connection", "keep-alive");
                    put("Cookie", "sid="+sessionId);
                }}));
        //when
        HttpResponse result = new FileController().createResponse(httpRequest);
        //then
        logger.debug("sid:"+sessionId);

        User user = SessionDb.getSession(sessionId).getUser();
        logger.debug(SessionDb.getSession(sessionId).toString());
        byte[] body = HtmlBuildUtil.buildHtml("/index.html", user);
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK,"HTTP/1.1"))
                .headers(new HttpResponseHeaders("/index.html", body.length))
                .body(new HttpResponseBody(body));

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.getBody().length).isEqualTo(httpResponse.getBody().length);
        softly.assertThat(result.getHeader().get("Cookie")).isEqualTo(httpResponse.getHeader().get("Cookie"));
        softly.assertAll();
    }
}
