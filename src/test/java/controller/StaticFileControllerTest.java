package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaticFileControllerTest {

    @Test
    void 파일타입요청_여부확인() {
        String url1 = "/hello/nice/to/meet/you";
        String url2 = "/hello/nice.html";
        String url3 = "/";

        boolean result1 = StaticFileController.ifFileTypeRequested(url1);
        boolean result2 = StaticFileController.ifFileTypeRequested(url2);
        boolean result3 = StaticFileController.ifFileTypeRequested(url3);

        assertFalse(result1);
        assertTrue(result2);
        assertFalse(result3);
    }
}