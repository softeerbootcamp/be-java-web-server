package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RequestDataTypeTest {

    @Test
    @DisplayName("url-type queryString 테스트")
    void findQueryString() {
        String url = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        RequestDataType urlType = RequestDataType.getUrlType(url);
        assertThat(urlType).isEqualTo(RequestDataType.QUERY_STRING);
    }

    @Test
    @DisplayName("url-type STATIC_FILE 테스트")
    void findSTATICFILE() {
        String url = "/bootstrap.js";
        RequestDataType urlType = RequestDataType.getUrlType(url);
        assertThat(urlType).isEqualTo(RequestDataType.STATIC_FILE);
    }

    @Test
    @DisplayName("url-type TEMPLATES_FILE 테스트")
    void findTEMPLATES_FILE() {
        String url = "/index.html";
        RequestDataType urlType = RequestDataType.getUrlType(url);
        assertThat(urlType).isEqualTo(RequestDataType.TEMPLATES_FILE);
    }
}