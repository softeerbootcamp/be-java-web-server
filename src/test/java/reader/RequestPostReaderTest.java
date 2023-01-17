package reader;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestPostReaderTest {

    private final byte[] BASIC_REQUEST_INCLUDE_BODY = ("POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=test&password=testPW&name=test&email=test@naver.com").getBytes();

    @Test
    @DisplayName("Post의 요청의 form데이터 읽기 테스트")
    void readData() throws IOException {
        //given
        ByteArrayInputStream inputStream = new ByteArrayInputStream(BASIC_REQUEST_INCLUDE_BODY);
        //when
        HttpRequest httpRequest = HttpRequest.getHttpRequest(inputStream);
        RequestReader requestReader = new RequestPostReader();
        HashMap<String, String> usermap = requestReader.readData(httpRequest);
        //then
        assertAll(
                () -> assertEquals(usermap.get("userId"), "test"),
                () -> assertEquals(usermap.get("password"), "testPW"),
                () -> assertEquals(usermap.get("name"), "test"),
                () -> assertEquals(usermap.get("email"), "test@naver.com")
        );

    }
}