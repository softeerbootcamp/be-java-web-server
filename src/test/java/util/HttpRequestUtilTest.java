package util;

import org.junit.jupiter.api.Test;

import java.util.Map;


class HttpRequestUtilTest {

   @Test
   public void parseQueryTest(){
        String query = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> result = HttpRequestUtil.parseQueryString(query);
        result.forEach((s1,s2)->System.out.println(s1+':'+s2));
   }
   @Test
    public void parseRequestTest(){
       //InputStream inputStream = new In
   }
}
