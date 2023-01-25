package request;

import http.request.HttpRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private final static String testDirectory = "./src/test/resources/";
    private static HttpRequest httpRequest;

    @BeforeAll
    static void setUp() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST_UserCreate.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        httpRequest = HttpRequest.of(br);
    }


    @Test
    void test_HttpRequest() {
        assertThat(httpRequest.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpRequest.getRequestBody().get("userId")).isEqualTo("mino");
        assertThat(httpRequest.getRequestBody().get("name")).isEqualTo("장민호");
        assertThat(httpRequest.getRequestBody().get("email")).isEqualTo("mino@naver.com");
        assertThat(httpRequest.getRequestBody().get("password")).isEqualTo("1234");
    }
}

