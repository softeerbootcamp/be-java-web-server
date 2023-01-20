package kr.codesquad.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ViewResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewResolverTest {

    @Test
    @DisplayName("파일의 확장자가 html인 경우 templates 디렉토리 경로를 반환하는지 검증한다.")
    void resolveViewNameToTemplatesDirectory() {
        String viewName = "index.html";
        String result = ViewResolver.resolveViewName(viewName);

        assertThat(result).isEqualTo("src/main/resources/templates" + viewName);
    }

    @Test
    @DisplayName("파일의 확장자가 html이 아닌 경우 static 디렉토리 경로를 반환하는지 검증한다.")
    void resolveViewNameToTemplatesStaticDirectory() {
        String viewName = "min.css";
        String result = ViewResolver.resolveViewName(viewName);

        assertThat(result).isEqualTo("src/main/resources/static" + viewName);
    }
}
