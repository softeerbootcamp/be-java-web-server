package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileTypeTest {

    @Test
    @DisplayName(".html 파일 확장자명 테스트")
    void getFileTypeHtml() throws FileNotFoundException {
        Url url = new Url("/index.html", UrlType.TEMPLATES_FILE);
        FileType fileType = FileType.getFileType(url);
        Assertions.assertThat(fileType).isEqualTo(FileType.HTML);
    }

    @Test
    @DisplayName(".css 파일 확장자명 테스트")
    void getFileTypeCss() throws FileNotFoundException {
        Url url = new Url("/index.css", UrlType.STATIC_FILE);
        FileType fileType = FileType.getFileType(url);
        Assertions.assertThat(fileType).isEqualTo(FileType.CSS);
    }

    @Test
    @DisplayName(".js 파일 확장자명 테스트")
    void getFileTypeJS() throws FileNotFoundException {
        Url url = new Url("/index.js", UrlType.STATIC_FILE);
        FileType fileType = FileType.getFileType(url);
        Assertions.assertThat(fileType).isEqualTo(FileType.JS);
    }
}