package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

class FileIoUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Test
    @DisplayName("/index.html 제대로 반환하는지 테스트")
    void loadFile() throws Exception {
        byte[] body = FileIoUtils.loadFile("/index.html");
        logger.debug("file : {}", new String(body));
    }

}
