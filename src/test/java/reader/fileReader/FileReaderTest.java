package reader.fileReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.RequestDataType;

import static org.assertj.core.api.Assertions.*;

class FileReaderTest {

    @Test
    @DisplayName("templates관련 url regex test")
    void selectFileReader() {
        String url = "/index.html";
        FileReader fileReader = FileReader.selectFileReader(RequestDataType.TEMPLATES_FILE);
        assertThat(fileReader).isInstanceOf(TemplatesFileReader.class);
    }
}