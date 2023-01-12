package util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestUtilTest {

    @Test
    void parseQueryStringTest() {
        String queryString = "userId=testId&password=testPW&name=testName&email=test%40mail.com";
        Map<String,String> info = HttpRequestUtil.parseQueryString(queryString);
        Map<String, String> user = new HashMap<>(){{
            put("userId", "testId");
            put("password", "testPW");
            put("name", "testName");
            put("email", "test%40mail.com");
        }};

        assertThat(info).isEqualTo(user);
    }

    @Test
    void getOnlyURLTest() {
        String reqURL = "/user/create?userId=testId&password=testPW&name=testName&email=test%40mail.com";
        String onlyURL = "/user/create";
        assertThat(HttpRequestUtil.getOnlyURL(reqURL)).isEqualTo(onlyURL);
    }

    @Test
    void getURLParamsTest() {
        String reqURL = "/user/create?userId=testId&password=testPW&name=testName&email=test%40mail.com";
        String onlyURL = "userId=testId&password=testPW&name=testName&email=test%40mail.com";
        assertThat(HttpRequestUtil.getURLParams(reqURL)).isEqualTo(onlyURL);
    }

    @Test
    void getURLParamsExceptionTest(){
        String reqURL = "/index.html";
        String onlyURL = "";
        assertThat(HttpRequestUtil.getURLParams(reqURL)).isEqualTo(onlyURL);
    }

    @Test
    void getRequestAttribute() {
        String onlyURL = "/user/create";
        String reqAttribute = "/create";
        assertThat(HttpRequestUtil.getRequestAttribute(onlyURL)).isEqualTo(reqAttribute);
    }
}