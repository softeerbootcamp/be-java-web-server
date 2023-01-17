package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.RequestDataType;
import request.Url;

class FileTypeTest {

    @Test
    @DisplayName(".html 파일 확장자명 테스트")
    void getFileTypeHtml()  {
        Url url = new Url("/index.html", RequestDataType.TEMPLATES_FILE);
        FileType fileType = FileType.getFileType(url);
        Assertions.assertThat(fileType).isEqualTo(FileType.HTML);
    }

    @Test
    @DisplayName(".css 파일 확장자명 테스트")
    void getFileTypeCss()  {
        Url url = new Url("/index.css",RequestDataType.STATIC_FILE);
        FileType fileType = FileType.getFileType(url);
        Assertions.assertThat(fileType).isEqualTo(FileType.CSS);
    }

    @Test
    @DisplayName(".js 파일 확장자명 테스트")
    void getFileTypeJS(){
        Url url = new Url("/index.js",RequestDataType.STATIC_FILE);
        FileType fileType = FileType.getFileType(url);
        Assertions.assertThat(fileType).isEqualTo(FileType.JS);
    }
}