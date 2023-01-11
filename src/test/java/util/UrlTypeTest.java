package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UrlTypeTest {

    @Test
    @DisplayName("url-type queryString 테스트")
    void findQueryString() {
        String url = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        UrlType urlType = UrlType.getUrlType(url);
        assertThat(urlType).isEqualTo(UrlType.QUERY_STRING);
    }

    @Test
    @DisplayName("url-type STATIC_FILE 테스트")
    void findSTATICFILE() {
        String url = "/bootstrap.js";
        UrlType urlType = UrlType.getUrlType(url);
        assertThat(urlType).isEqualTo(UrlType.STATIC_FILE);
    }

    @Test
    @DisplayName("url-type TEMPLATES_FILE 테스트")
    void findTEMPLATES_FILE() {
        String url = "/index.html";
        UrlType urlType = UrlType.getUrlType(url);
        assertThat(urlType).isEqualTo(UrlType.TEMPLATES_FILE);
    }
}