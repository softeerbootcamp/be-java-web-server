package util;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Map;


class HttpRequestParserTest {
    @Test
    public void mappingPathTest(){
        HttpRequestParser httpRequestParser = new HttpRequestParser();
        Path result = httpRequestParser.mappingPath("/css/styles.css");
        System.out.println(result);
   }
   @Test
   public void parseQueryTest(){
        String query = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> result = HttpRequestParser.parseQuery(query);
        result.forEach((s1,s2)->System.out.println(s1+':'+s2));
   }
}
