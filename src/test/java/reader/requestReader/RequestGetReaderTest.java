package reader.requestReader;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reader.fileReader.FileReader;
import request.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestGetReaderTest {

    private final byte[] PATH_VARIABLE_REQUEST = ("GET /test/chat/3 HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*").getBytes();

    @Test
    @DisplayName("PathVariable 데이터 읽기 테스트")
    void readData() throws IOException {
        //given
        ByteArrayInputStream inputStream = new ByteArrayInputStream(PATH_VARIABLE_REQUEST);
        HttpRequest httpRequest = HttpRequest.getHttpRequest(inputStream);
        //when
        RequestReader requestReader = new RequestGetReader();
        HashMap<String, String> data = requestReader.readData(httpRequest);
        //then
        String value = data.get(RequestGetReader.PATH_VARIABLE_KEY);
        assertThat(value).isEqualTo("3");
    }
}