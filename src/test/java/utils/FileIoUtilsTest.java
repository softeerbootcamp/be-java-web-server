package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FileIoUtilsTest {

    @Test
    @DisplayName("존재하는 파일 요청 시 제대로 작동하는지 테스트")
    void loadFileSuccessTest()  {
        assertThatCode(() -> FileIoUtils.loadFile("/index.html"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하지 않는 파일 요청시 실패하는지 테스트")
    void loadFileFailTest() {
        assertThatThrownBy(() -> FileIoUtils.loadFile("/test.html"));
    }

}
