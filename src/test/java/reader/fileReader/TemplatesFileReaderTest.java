package reader.fileReader;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.RequestDataType;
import request.Url;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class TemplatesFileReaderTest {

    @Test
    @DisplayName("resources/templates 내의 있는 file 읽기")
    void readFile() throws IOException {
        String url = "/index.html";
        FileReader fileReader = new TemplatesFileReader();
        assertThat(fileReader.readFile(new Url(url, RequestDataType.FILE))).isEqualTo(
                Files.readAllBytes(new File("./src/main/resources/templates" + url).toPath())
        );
    }

    @Test
    @DisplayName("url이 .html이 없는경우 테스트")
    void testNotIncludeHtmlRead() throws IOException {
        //given
        Url url = new Url("/index", RequestDataType.FILE);
        //when
        FileReader fileReader = new TemplatesFileReader();
        byte[] data = fileReader.readFile(url);
        //then
        assertThat(data).isEqualTo(
                Files.readAllBytes(new File("./src/main/resources/templates" + url.getUrl() + ".html").toPath())
        );
    }

    @Test
    @DisplayName("url이 pathVariable인경우 뒤의 데이터를 무시하고 html파일 읽기 테스트")
    void testPathVariableTest() throws IOException {
        //given
        Url url = new Url("/qna/show/34", RequestDataType.PATH_VARIABLE);
        //when
        FileReader fileReader = new TemplatesFileReader();
        byte[] data = fileReader.readFile(url);
        //then
        assertThat(data).isEqualTo(
                Files.readAllBytes(new File("./src/main/resources/templates/qna/show.html").toPath())
        );
    }
}