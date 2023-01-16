package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpResponseUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtilTest.class);
    @Test
    public void generateBodyTest(){
        try {
            //given
            String path = "/css/styles.css";
            //when
            byte[] body = HttpResponseUtil.generateBody(path);
            byte[] result = Files.readAllBytes(new File("/Users/rentalhub/missions/week3/be-java-web-server/src/main/resources/static/css/styles.css").toPath());
            //then
            Assertions.assertThat(result).isEqualTo(body);
        }catch (NullPointerException e){
            logger.error(e.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
