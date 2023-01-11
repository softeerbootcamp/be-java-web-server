package service;

import db.Database;
import db.UserDatabase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

class UserServiceAndDBTest {
    final String[] userValue = {"test", "1234", "testName", "test@naver.com"};
    final String[] userValue2 = {"test2", "1234", "test2Name", "tes2t@naver.com"};
    final String[] userKey = {"userId", "password", "name", "email"};

    Service service = new UserService();
    Database userDataBase = new UserDatabase();




    @Test
    @DisplayName("회원가입 요청시 사용자 정보 저장")
    void saveUserData() {
        //given
        saveUser(userValue);
        //when
        User user = userDataBase.findObjectById(userValue[0]);
        //then
        assertThat(user.getUserId()).isEqualTo(userValue[0]);
        assertThat(user.getPassword()).isEqualTo(userValue[1]);
        assertThat(user.getName()).isEqualTo(userValue[2]);
        assertThat(user.getEmail()).isEqualTo(userValue[3]);
    }

    private void saveUser(String[] userValue) {
        HashMap<String, String> userMap = new HashMap<>();
        for (int i = 0; i < userKey.length; i++) {
            userMap.put(userKey[i],userValue[i]);
        }
        //when
        service.saveData(userMap);
    }

    @Test
    @DisplayName("사용자 정보 모두 조회")
    void findAll() {
        //given
        saveUser(userValue);
        saveUser(userValue2);
        //when
        Collection<Object> all = userDataBase.findAll();

        //then
        assertThat(all.size()).isEqualTo(2);
    }
}