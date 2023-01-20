package reader.fileReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.RequestDataType;
import request.Url;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.*;

class StaticFileReaderTest {

    private static final String VALID_CSS_URL = "/css/styles.css";
    private static final String NOT_VALID_CSS_URL = "/css/notvalid.css";
    private static final String RELATIVE_PATH = "./src/main/resources/static";

    @Test
    @DisplayName("resources/static 내의 있는 file 읽기")
    void readFile() throws IOException {
        String url = VALID_CSS_URL;
        FileReader fileReader = new StaticFileReader();
        assertThat(fileReader.readFile(new Url(url, RequestDataType.FILE))).isEqualTo(
                Files.readAllBytes(new File(RELATIVE_PATH + url).toPath())
        );
    }

    @Test
    @DisplayName("resources/static 내의 있는 file읽기중 예외 테스트")
    void readFileThrowException()  {
        String url = NOT_VALID_CSS_URL;
        FileReader fileReader = new StaticFileReader();
        assertThatThrownBy(() -> fileReader.readFile(new Url(url,RequestDataType.FILE)))
                .isInstanceOf(IOException.class);
    }
}