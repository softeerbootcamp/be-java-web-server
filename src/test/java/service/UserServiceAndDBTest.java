package service;

import db.Database;
import db.UserDatabase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceAndDBTest {
    final String[] userValue = {"test", "1234", "testName", "test@naver.com"};
    final String[] userValue2 = {"test2", "1234", "test2Name", "tes2t@naver.com"};
    final String[] userKey = {"userId", "password", "name", "email"};

    Database<User> userDataBase = new UserDatabase();
    Service userService = new UserService();




    @Test
    @DisplayName("회원가입 요청시 사용자 정보 저장")
    void saveUserData() {
        //given
        saveUser(userValue);
        //when
        Optional<User> objectById = userDataBase.findObjectById(userValue[0]);
        User user = objectById.get();
        //then
        assertAll(
                () -> assertEquals(user.getUserId(), userValue[0]),
                () -> assertEquals(user.getPassword(), userValue[1]),
                () -> assertEquals(user.getName(), userValue[2]),
                () -> assertEquals(user.getEmail(), userValue[3])
        );
    }

    private void saveUser(String[] userValue) {
        HashMap<String, String> userMap = new HashMap<>();
        for (int i = 0; i < userKey.length; i++) {
            userMap.put(userKey[i],userValue[i]);
        }
        User user = (User) userService.createModel(userMap);
        //when
        userDataBase.addData(user);
    }

    @Test
    @DisplayName("사용자 정보 모두 조회")
    void findAll() {
        //given
        saveUser(userValue);
        saveUser(userValue2);
        //when
        Collection<User> all = userDataBase.findAll();
        assertThat(all.size()).isEqualTo(2);
    }
}