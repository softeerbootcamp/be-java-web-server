package utils;

import enums.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExtensionUtilsTest {

    @Test
    @DisplayName("정상 동작 테스트")
    void extractExtension() {
        //given
        String str ="hello.html";

        //when
        ContentType type = ExtensionUtils.extractExtension(str);

        //then
        assertThat(type).isEqualTo(ContentType.HTML);
    }

    @Test
    @DisplayName("해당 타입이 없을 때의 예외 테스트")
    void extractNoExtension() {
        //given
        String str ="hello.icc";

        //when, then
        assertThrows(IllegalArgumentException.class,()->{
            ExtensionUtils.extractExtension(str);
        });
    }
}
