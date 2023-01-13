package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ParameterTest {
    @Test
    @DisplayName("파라미터가 존재할 때 생성 테스트")
    void createParameterTest() {
        String queryString = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Parameter parameter = Parameter.createParam(queryString);
        assertAll(
                () -> assertThat(parameter.getParameter("userId")).isEqualTo("javajigi"),
                () -> assertThat(parameter.getParameter("password")).isEqualTo("password"),
                () -> assertThat(parameter.getParameter("name")).isEqualTo("박재성"),
                () -> assertThat(parameter.getParameter("email")).isEqualTo("javajigi@slipp.net"),
                () -> assertThat(parameter.hasParameter()).isTrue()
        );
    }

    @Test
    @DisplayName("파라미터가 존재하지 않을 때 생성 테스트")
    void createEmptyParameterTest() {
        Parameter parameter = Parameter.createEmptyParam();
        assertAll(
                () -> assertThat(parameter.getParameters()).isEqualTo(Collections.emptyMap()),
                () -> assertThat(parameter.hasParameter()).isFalse()
        );
    }

}
