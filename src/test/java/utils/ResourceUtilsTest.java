package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.ResourceUtils;

import static org.junit.jupiter.api.Assertions.assertAll;
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
        assertEquals(5218, bytes.length);
    }

    @Test
    @DisplayName("loadFileFromClasspath() - static 디렉토리 파일 탐색 성공 케이스")
    void loadStaticFileFromClasspath() {
        // given
        String pathOfCss = "/css/styles.css";
        String pathOfJS = "/js/bootstrap.min.js";

        // when
        byte[] css = ResourceUtils.loadFileFromClasspath(pathOfCss);
        byte[] js = ResourceUtils.loadFileFromClasspath(pathOfJS);

        // then
        assertAll(
                () -> assertEquals(7065, css.length),
                () -> assertEquals(31819, js.length)
        );
    }

    @Test
    @DisplayName("loadFileFromClasspath() - 파일이 존재하지 않는 케이스")
    void NoSearchFile() {
        // given
        String invalidPath = "/no/search/file";

        // when
        byte[] nodata = ResourceUtils.loadFileFromClasspath(invalidPath);

        // then
        assertEquals(0, nodata.length);
    }
}
