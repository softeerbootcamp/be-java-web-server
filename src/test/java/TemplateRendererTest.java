import dto.Comment;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.TemplateRenderer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class TemplateRendererTest {

    @Test
    @DisplayName("정적 페이지와 동일한 내용으로 동적 템플릿 생성이 되는지 테스트")
    public void generatePage() throws IOException {
        SoftAssertions softly = new SoftAssertions();
        // given
        TemplateRenderer template = new TemplateRenderer("src/main/resources/templates/index.html");
        softly.assertThat(template.toString()).isEqualTo(new File("src/main/resources/templates/index.html").toString());
    }

    @Test
    @DisplayName("생성된 페이지에서 toReplace String을 찾고, given 스트링으로 바꾸는 메서드 테스트")
    public void regeneratePageTest() throws IOException {
        SoftAssertions softly = new SoftAssertions();

        // given
        TemplateRenderer template = new TemplateRenderer("src/main/resources/templates/index.html");
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/templates/index.html"));
        String line = "";

        List<Comment> comments = List.of(new Comment(1, "Hello", "gildong", "gildong", LocalDateTime.now()),
                new Comment(2, "World!", "hong", "hong", LocalDateTime.now()));

        // when
        template.replaceStringWithGivenString("로그인", "홍길동");
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= comments.size(); ++i) {
            Comment comment = comments.get(i - 1);
            String item = String.format(
                    "                        <th scope=\"row\"><span class=\"time\">%s</span></th>\n" +
                            "                        <td class=\"auth-info\">\n" +
                            "\n" +
                            "                            <a href=\"./user/profile.html\" class=\"author\">%s</a>\n" +
                            "                        </td>\n" +
                            "                        <td class=\"body\">\n" +
                            "                            <span>%s</span>\n" +
                            "                        </td>\n", comment.getCreateDate(), comment.getUserName(), comment.getBody());

            sb.append("<tr>");
            sb.append(item);
            sb.append("        </tr>");
        }

        template.replaceStringWithGivenString("<!-- %comment% -->", sb.toString());

        softly.assertThat(template.toString()).doesNotContain("<!-- %comment% -->").doesNotContain("로그인");
    }
}
