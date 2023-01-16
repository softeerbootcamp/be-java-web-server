package util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MessageParserTest {

    @Test
    void parseQueryStringTest() {
        String queryString = "userId=testId&password=testPW&name=testName&email=test%40mail.com";
        Map<String,String> info = MessageParser.parseQueryString(queryString);
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
        assertThat(MessageParser.getOnlyURL(reqURL)).isEqualTo(onlyURL);
    }

    @Test
    void getURLParamsTest() {
        String reqURL = "/user/create?userId=testId&password=testPW&name=testName&email=test%40mail.com";
        String onlyURL = "userId=testId&password=testPW&name=testName&email=test%40mail.com";
        assertThat(MessageParser.getURLParams(reqURL)).isEqualTo(onlyURL);
    }

    @Test
    void getURLParamsExceptionTest(){
        String reqURL = "/index.html";
        String onlyURL = "";
        assertThat(MessageParser.getURLParams(reqURL)).isEqualTo(onlyURL);
    }

    @Test
    void getRequestAttributeTest() {
        String onlyURL = "/user/create";
        String reqAttribute = "/create";
        assertThat(MessageParser.getRequestAttribute(onlyURL)).isEqualTo(reqAttribute);
    }


}