import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.HtmlService;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlServiceTest {
    @Test
    @DisplayName("동적 html 변환 테스트")
    void 동적_HTMl_변환_테스트() {
        // given
        String indexHtmlResponseString = "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>\n" +
                "                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>";
        String HtmlInUserFolderResponseString = "<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>\n" +
                "                <li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>";
        User logInUser = new User("testid", "testpassword", "홍길동", "asdf@naver.com");

        // when
        byte[] indexHtmlReplacedString =
                HtmlService.banLogInAndSignUpWhenUserLogInState(indexHtmlResponseString, logInUser);
        byte[] HtmlInUserFolderReplacedString =
                HtmlService.banLogInAndSignUpWhenUserLogInState(HtmlInUserFolderResponseString, logInUser);

        // then
        byte[] result = ("<li><a role=\"button\">" + logInUser.getName() + "</a></li>").getBytes();
        assertThat(indexHtmlReplacedString).isEqualTo(result);
        assertThat(HtmlInUserFolderReplacedString).isEqualTo(result);
    }
}
