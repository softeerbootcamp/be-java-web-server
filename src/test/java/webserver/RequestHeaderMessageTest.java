package webserver;

import org.junit.jupiter.api.Test;
import view.RequestHeaderMessage;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderMessageTest {

    final String userCreate = "GET /user/create?userId=asdf&password=ljh1929&name=%EC%9D%B4%EC%9E%AC%ED%9B%88&email=wogns1602%40naver.com HTTP/1.1";
    @Test
    void getHttpOnlyURL() {
        RequestHeaderMessage requestHeaderMessage = new RequestHeaderMessage(userCreate);
        assertThat(requestHeaderMessage.getHttpOnlyURL()).isEqualTo("/user/create");
    }

    @Test
    void getHttpReqParams() {
        RequestHeaderMessage requestHeaderMessage = new RequestHeaderMessage(userCreate);
        assertThat(requestHeaderMessage.getHttpReqParams()).isEqualTo("userId=asdf&password=ljh1929&name" +
                "=%EC%9D%B4%EC%9E%AC%ED%9B%88&email=wogns1602%40naver.com");
    }

    @Test
    void getFileExtension() {
        RequestHeaderMessage requestHeaderMessage = new RequestHeaderMessage("GET /index.html HTTP/1.1");
        assertThat(requestHeaderMessage.getFileExtension()).isEqualTo("html");
    }
}