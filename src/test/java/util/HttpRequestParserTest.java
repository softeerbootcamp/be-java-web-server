package util;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;


class HttpRequestParserTest {
    @Test
    public void mappingPathTest(){
        HttpRequestParser httpRequestParser = new HttpRequestParser();
        Path result = httpRequestParser.mappingPath("/css/styles.css");
        System.out.println(result);
   }
}
