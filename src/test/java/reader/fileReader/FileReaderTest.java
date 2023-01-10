package reader.fileReader;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

    @Test
    @DisplayName("templates관련 url regex test")
    void selectFileReader() {
        String url = "/index.html";
        FileReader fileReader = FileReader.selectFileReader(url);
        assertThat(fileReader).isInstanceOf(TemplatesFileReader.class);
    }
}