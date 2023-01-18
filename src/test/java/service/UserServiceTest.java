package service;

import db.Database;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @Test
    void addUser_테스트() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", "ajongs");
        map.put("password", "password");
        map.put("name", "박원종");
        map.put("email", "wonjong1030@naver.com");
        UserService userService = UserService.getInstance();
        userService.addUser(map);

        User user = Database.findUserById(map.get("userId"));
        Assertions.assertThat(user.getUserId()).isEqualTo("ajongs");
        Assertions.assertThat(user.getPassword()).isEqualTo("password");
        Assertions.assertThat(user.getName()).isEqualTo("박원종");
        Assertions.assertThat(user.getEmail()).isEqualTo("wonjong1030@naver.com");
    }
}