package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static util.ViewResolver.findFilePath;

public class ViewResolverTest {

    @Test
    @DisplayName("templates 디렉토리 내 resolving (index.html)")
    void resolveTemplatesPath() throws IOException {
        Path filePath = findFilePath("index.html");

        Assertions.assertThat(filePath.toString())
                .isEqualTo("/Users/rentalhub-mac34/Documents/min/be-java-web-server/src/main/resources/templates/index.html");
    }

    @Test
    @DisplayName("static 디렉토리 내 resolving (css)")
    void resolveStaticPath() throws IOException {
        Path filePath = findFilePath("css/styles.css");

        Assertions.assertThat(filePath.toString())
                .isEqualTo("/Users/rentalhub-mac34/Documents/min/be-java-web-server/src/main/resources/static/css/styles.css");
    }
}
