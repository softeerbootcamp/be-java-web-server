package util;

import Request.HttpRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

class HttpRequestUtilTest {
    private final Logger logger = LoggerFactory.getLogger(HttpRequestUtilTest.class);
   @Test
   public void parseQueryTest(){
        String query = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> result = HttpRequestUtil.parseQueryString(query);
        result.forEach((s1,s2)->System.out.println(s1+':'+s2));
   }
   @Test
    public void parseRequestTest(){
       InputStream in = new ByteArrayInputStream("GET /user/create?userId=jieon&password=dfs&name=kim&email=kim%40naver.com HTTP/1.1".getBytes());
       try {
           HttpRequest httpRequest = HttpRequestUtil.parseRequest(in);
           Assertions.assertThat(httpRequest.getParams().get("userId").equals("jieon"));
       } catch (IOException e) {
           logger.error(e.getMessage());
       }
   }
}
