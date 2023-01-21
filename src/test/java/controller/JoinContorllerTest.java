package controller;

import Controller.JoinController;
import Request.*;
import Response.HttpResponse;
import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpRequestUtil;
import util.UserDbUtil;

import java.util.HashMap;

public class JoinContorllerTest {
    @Test
    @DisplayName("회원가입 테스트")
    void joinTest(){
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
        //when
        JoinController joinController = JoinController.getInstance();
        HttpResponse response = joinController.createResponse(joinRequest);
        //then
        User user = UserDbUtil.findUserById("javajigi");
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(user).isNotNull();
        softly.assertThat(response.getStatusCode()).isEqualTo(StatusCode.FOUND);
        softly.assertAll();
    }
}
