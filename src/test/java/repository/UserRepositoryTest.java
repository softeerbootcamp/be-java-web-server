package repository;

import db.Database;
import db.UserRepository;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRepositoryTest {

    @Test
    @DisplayName("유저정보 저장 테스트")
    void userSave() throws Exception {
        //given
        User user = new User("test", "123123", "tester", "test.com");

        //when
        UserRepository userRepository = new UserRepository();
        userRepository.insert(user);

        //then
//        Assertions.assertThat();
    }

    @Test
    @DisplayName("xml 읽을 수 있는지")
    void xml() throws Exception {
        //given
        Database database = Database.getInstance();
        //when
        Assertions.assertThat(database.getUSER()).isEqualTo("root");
    }

}
