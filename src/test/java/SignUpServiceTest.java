import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.SignUpService;

import static org.assertj.core.api.Assertions.assertThat;

public class SignUpServiceTest {
    @Test
    @DisplayName("회원 가입 시 유저 정보들을 유저 클래스에 잘 담아 주는지 테스트")
    void makeUserInfoTest() {
        // given
        final String uri = "/user/create?userId=jhchoi57&" +
                "password=12349865&" +
                "name=%EC%B5%9C%EC%A3%BC%ED%98%95&" +
                "email=jhchoi57%40gmail.com";

        // when
        final User user = SignUpService.makeUserInfo(uri);

        // then
        assertThat(user).usingRecursiveComparison().isEqualTo(new User(
                "jhchoi57", "12349865", "최주형", "jhchoi57@gmail.com"));
    }

}
