package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.ResourceUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ResourceUtils Test")
public class ResourceUtilsTest {

    @Test
    @DisplayName("loadFileFromClasspath() - templates 디렉토리 파일 탐색 성공 케이스")
    void loadTemplateFileFromClasspath() {
        // given
        String filePath = "/index.html";

        // when
        byte[] bytes = ResourceUtils.loadFileFromClasspath(filePath);

        // then
        assertEquals(6902, bytes.length);
    }

    @Test
    @DisplayName("loadFileFromClasspath() - static 디렉토리 파일 탐색 성공 케이스")
    void loadStaticFileFromClasspath() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("loadFileFromClasspath() - 파일이 존재하지 않는 케이스")
    void NoSearchFile() {
        // given

        // when

        // then
    }
}
