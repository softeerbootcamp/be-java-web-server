package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

//    @DisplayName("template 경로 테스트")
//    @Test
//    void 템플릿_경로_테스트() {
//        RequestStartLine requestLine = RequestStartLine.of("GET /index.html HTTP/1.1");
//        Map<String, String> headerFields = new HashMap<>();
//        headerFields.put("Host", "localhost:8080");
//        headerFields.put("Connection", "keep-alive");
//        headerFields.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
////        RequestHeader requestHeader = RequestHeader.of(headerFields);
////        header.append(headerFields);
////
////        HttpRequest httpRequest = new HttpRequest(requestLine, header.toString());
////        String path = httpRequest.getPath();
////        assertThat(path).isEqualTo("./templates/index.html");
//    }
//
//    @DisplayName("Static 경로 테스트")
//    @Test
//    void 스태틱_경로_테스트() {
////        RequestStartLine requestStartLine = RequestStartLine.of("GET /css/styles.css HTTP/1.1");
//        Map<String, String> headerFields = new HashMap<>();
//        headerFields.put("Host", "localhost:8080");
//        headerFields.put("Connection", "keep-alive");
//        headerFields.put("Accept", "text/css,*/*;q=0.1");
//        StringBuilder header = new StringBuilder();
//        header.append(headerFields);
//
//        HttpRequest httpRequest = new HttpRequest(requestStartLine, header.toString());
//        String path = httpRequest.getPath();
//
//        assertThat(path).isEqualTo("./static/css/styles.css");
//    }
//
//    @Test
//    void getPath_whenRequestNotResources() {
//        RequestStartLine requestLine = RequestStartLine.of(
//                "POST /user/create?userId=user&password=&name=email= HTTP/1.1");
//        Map<String, String> headerFields = new HashMap<>();
//        headerFields.put("Host", "localhost:8080");
//        headerFields.put("Connection", "keep-alive");
//        headerFields.put("Accept", "*/*");
//        Header header = new Header(headerFields);
//        HttpRequest httpRequest = new HttpRequest(requestLine, header.toString());
//
//        String path = httpRequest.getPath();
//
//        assertThat(path).isEqualTo("/user/create");
//    }
}
