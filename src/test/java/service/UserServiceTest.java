package service;

import dto.UserInfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private final UserService userService = new UserService();

    @Test
    @DisplayName("회원가입 성공")
    void success_signup() {
        // given
        UserInfoDTO userInfoDTO = getUserInfoDTO(1);
        // when
        String userId = userService.signIn(userInfoDTO);
        // then
        assertThat(userInfoDTO.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("회원가입 실패: 이미 가입한 아이디")
    void fail_signup_duplicated_id() {
        // given
        UserInfoDTO userInfoDTO1 = getUserInfoDTO(1);
        UserInfoDTO userInfoDTO2 = getUserInfoDTO(1);

        // when
        userService.signIn(userInfoDTO1);
        String userId = userService.signIn(userInfoDTO2);
        // then
        assertThat(userId).isNull();
    }

    private UserInfoDTO getUserInfoDTO(Integer postFix) {
        Map<String, String> userInfo = Map.of(
                "userId", "id" + postFix,
                "password", "pw" + postFix,
                "name", "n" + postFix,
                "email", "e" + postFix
        );
        return UserInfoDTO.of(userInfo);
    }
}