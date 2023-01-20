import org.junit.Test;
import request.HandlerEnum;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RegExTest {
    @Test
    public void 파일정규식() {
        String templateUrl1 = "/user/login.html";
        String templateUrl2 = "/user/login.ico";
        String staticUrl1 = "/css/hello.css";
        String staticUrl2 = "/js/hello.js";

        //assertThat(templateUrl1.matches(HandlerEnum.TEMPLATE.getUrlRegEx())).isEqualTo(true);
        //assertThat(templateUrl2.matches(HandlerEnum.TEMPLATE.getUrlRegEx())).isEqualTo(true);
        assertThat(staticUrl1.matches(HandlerEnum.STATIC.getUrlRegEx())).isEqualTo(true);
        assertThat(staticUrl2.matches(HandlerEnum.STATIC.getUrlRegEx())).isEqualTo(true);
    }

    @Test
    public void 요청정규식() {
        String loginUrl = "/user/login";
        String registerUrl = "/user/create";
        String registerUrl2 = "/user/create/";
        String indexUrl1 = "/index";
        String indexUrl2 = "/index.html";
        String indexUrl3 = "/index.htmml";

        assertThat(loginUrl.matches(HandlerEnum.LOGIN.getUrlRegEx())).isEqualTo(true);
        assertThat(registerUrl.matches(HandlerEnum.REGISTER.getUrlRegEx())).isEqualTo(true);
        assertThat(registerUrl2.matches(HandlerEnum.REGISTER.getUrlRegEx())).isEqualTo(false);
        assertThat(indexUrl1.matches(HandlerEnum.INDEX.getUrlRegEx())).isEqualTo(true);
        assertThat(indexUrl2.matches(HandlerEnum.INDEX.getUrlRegEx())).isEqualTo(true);
        assertThat(indexUrl3.matches(HandlerEnum.INDEX.getUrlRegEx())).isEqualTo(false);
    }
}
