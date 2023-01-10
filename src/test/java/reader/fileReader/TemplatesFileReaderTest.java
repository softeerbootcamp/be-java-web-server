package reader.fileReader;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reader.RequestReader;
import util.HttpMethod;
import util.Url;
import util.UrlType;
import util.error.HttpsErrorMessage;

import java.io.File;
import java.io.IOException;
import java.net.ProtocolException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TemplatesFileReaderTest {

    @Test
    @DisplayName("resources/templates 내의 있는 file 읽기")
    void readFile() throws IOException {
        String url = "/index.html";
        FileReader fileReader = new TemplatesFileReader();
        Assertions.assertThat(fileReader.readFile(new Url(url, UrlType.TEMPLATES_FILE))).isEqualTo(
                Files.readAllBytes(new File("./src/main/resources/templates" + url).toPath())
        );
    }
}