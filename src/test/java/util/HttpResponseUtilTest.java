package util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

public class HttpResponseUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtilTest.class);
    @Test
    public void generateBodyTest(){
        try {
            Path path = new File("./src/main/resources/static/css/styles.css").toPath();
            byte[] body = HttpResponseUtil.generateBody(path);
            System.out.println(new String(body));
            body = HttpResponseUtil.generateBody(null);
        }catch (NullPointerException e){
            logger.error(e.toString());
        }

    }
}
