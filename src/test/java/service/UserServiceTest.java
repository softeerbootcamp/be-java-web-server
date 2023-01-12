package service;

import db.Database;
import dto.UserInfoDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private final UserService userService = new UserService();

    private final Stack<String> userIds = new Stack<>();

    @AfterEach
    void afterEach() {
        while (userIds.size() != 0) {
            String id = userIds.pop();
            Database.deleteUser(id);
        }
    }

    @Test
    @DisplayName("회원가입 성공")
    void success_signup() {
        // given
        UserInfoDTO userInfoDTO = getUserInfoDTO(1);
        // when
        String userId = userService.signIn(userInfoDTO);
        userIds.add(userId);
        // then
        assertThat(userInfoDTO.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("회원가입 실패: 이미 가입한 아이디")
    void fail_signup_duplicated_id() {
        // given
        List<UserInfoDTO> userInfoDTOs = List.of(getUserInfoDTO(1), getUserInfoDTO(1));

        // when
        for (UserInfoDTO userInfoDTO : userInfoDTOs) {
            String userId = userService.signIn(userInfoDTO);
            userIds.add(userId);
        }
        // then
        assertThat(userIds.lastElement()).isNull();
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