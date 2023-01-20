import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


class FileIoUtilsTest {

    @Test
    @DisplayName("존재하는 파일 로딩하기")
    void testLoadFile_validFile() throws IOException {
        // given
        String file = "/index.html";

        // when
        byte[] result = FileIoUtils.loadFile(file);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("존하지 않은 파일 로딩하기")
    void testLoadFile_invalidFile() {
        // given
        String file = "/not_existing_file.html";

        // when, then
        assertThrows(IllegalArgumentException.class, () -> FileIoUtils.loadFile(file));
    }

    @Test
    @DisplayName("Extension 받아오기")
    void test_Extension() {
        //given
        String file = "hello/index.html";

        //when
        String extension = FileIoUtils.getExtension(file);

        //then
        assertThat(extension).isEqualTo("html");
    }
}
