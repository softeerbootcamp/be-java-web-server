package controller;

import db.SessionStorage;
import db.UserDAO;
import exception.SessionNotFoundException;
import model.User;
import model.request.Request;
import model.request.UserCreate;
import model.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.DynamicHtmlController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static model.response.HttpStatusCode.OK;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRequestHandlerTest {

    UserDAO userDAO = new UserDAO();

    @BeforeEach
    void cleanDb() throws SQLException {
        userDAO.deleteAll();
        SessionStorage.cleanAll();
    }

    @Test
    @DisplayName("입력된 request line에서 url 추출하는 메서드")
    void extractUrl() throws IOException {
        //given
        String requestMessage = "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n";
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        //when
        Request request = new Request(inputStream);
        String url = request.getUrl();

        //then
        assertThat(url).isEqualTo("/index.html");

    }

    @Test
    @DisplayName("입력된 request line이 없을 경우 extractUrl 메서드가 index를 반환하는지 여부 체크")
    void extractUrl_noUrl() throws IOException {
        //given
        String requestMessage = "GET / HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*";
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());

        //when
        Request request = new Request(inputStream);
        String url = request.getUrl();
        //then
        assertThat(url).isEqualTo("/index.html");

    }

    @Test
    @DisplayName("잘못된 쿠키값 브라우저에서 인덱스.html로 전송시 무시")
    void session_expired() throws Exception {
        //given
        userDAO.insert(new UserCreate("javajigi", "password", "tester", "test@test.com"));

        //when
        String sid = "123456";
        String requestMessage = "GET / HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*"
                + "Cookie: sid=" + sid;
        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());
        Request request = new Request(inputStream);

        DynamicHtmlController dynamicHtmlController = new DynamicHtmlController();
        Response response = dynamicHtmlController.service(request);
        //expected
        assertThat(response.getStatusLine().getHttpStatusCode()).isEqualTo(OK);

        Assertions.assertThrows(SessionNotFoundException.class, () -> SessionStorage.findBySessionId(sid));
    }

}
