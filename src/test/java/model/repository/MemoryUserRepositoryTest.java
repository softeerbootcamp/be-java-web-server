package model.repository;

import model.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryUserRepositoryTest {
    MemoryUserRepository memoryUserRepository = (MemoryUserRepository) MemoryUserRepository.getInstance();

    @AfterEach
    public void afterEach(){
        memoryUserRepository.clearUsers();
    }

    @Test
    void addUserTest() {
        User user = new User("testId","testPW","testName","test@mail.com");
        memoryUserRepository.addUser(user);
        assertThat(memoryUserRepository.findUserById(user.getUserId()).get()).isEqualTo(user);
    }

    @Test
    void findUserByIdTest() {
        User user1 = new User("testId1","testPW","testName","test@mail.com");
        User user2 = new User("testId2","testPW","testName","test@mail.com");
        memoryUserRepository.addUser(user1);
        assertThat(memoryUserRepository.findUserById("testId1").get()).isEqualTo(user1);
    }

    @Test
    void findAll() {
        User user1 = new User("testId1","testPW","testName","test@mail.com");
        User user2 = new User("testId2","testPW","testName","test@mail.com");
        memoryUserRepository.addUser(user1);
        memoryUserRepository.addUser(user2);

        Collection<User> result = memoryUserRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
