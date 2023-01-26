package db;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest {

    @DisplayName("유저 저장 테스트")
    @Test
    void UserAddTest() {
        //given
        User user1 = new User("qwer", "qwer", "qwer", "qwer@qwer.com");

        //when
        UserRepository.addUser(user1);

        //then
        User user2 = UserRepository.findUserById("qwer");
        assertEquals(user2.getUserId(), "qwer");
        assertEquals(user2.getPassword(), "qwer");
        assertEquals(user2.getName(), "qwer");
        assertEquals(user2.getEmail(), "qwer@qwer.com");

        UserRepository.deleteAll();
    }

    @DisplayName("전체 유저 탐색 테스트")
    @Test
    void UserFindAllTest() {
        //given
        User user1 = new User("user1", "user1", "user1", "user1@user1.com");
        User user2 = new User("user2", "user2", "user2", "user2@user2.com");
        User user3 = new User("user3", "user3", "user3", "user3@user3.com");

        //when
        UserRepository.addUser(user1);
        UserRepository.addUser(user2);
        UserRepository.addUser(user3);

        //then
        Collection<User> users = UserRepository.findAll();
        assertEquals(users.size(), 3);

        UserRepository.deleteAll();
    }

}
