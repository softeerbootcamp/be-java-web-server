package filesystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PathParserTest {

    @Test
    @DisplayName("정상적인 static 파일명은 template 또는 static 경로에 속한다")
    void success_normalFilePath() {
        // given
        List<String> fileNames = getFileNames();

        // when, then
        assertThat(fileNames.stream()
                .allMatch(f -> PathResolver.parse(f).contains("template") || PathResolver.parse(f).contains("static")))
                .isTrue();
    }

    private List<String> getFileNames() {
        List<String> fileNames = Extension.TEMPLATE.getExtensions().stream().map(e -> "index." + e).collect(Collectors.toList());
        fileNames.addAll(Extension.STATIC.getExtensions().stream().map(e -> "index." + e).collect(Collectors.toList()));
        return fileNames;
    }

}
