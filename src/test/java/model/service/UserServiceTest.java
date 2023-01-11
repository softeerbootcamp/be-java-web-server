package model.service;

import model.domain.User;
import model.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService;
    MemoryUserRepository memoryUserRepository;

    @BeforeEach
    public void beforeEach(){
        memoryUserRepository = new MemoryUserRepository();
        userService = new UserService(memoryUserRepository);
    }

    @AfterEach
    public void afterEach(){
        memoryUserRepository.clearUsers();
    }
    @Test
    void joinTest() {
        User user = new User("testId","testPW","testName","test@mail.com");
        userService.join(user);
        User findUser = userService.findOneUser("testId").get();
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    void joinDuplicateExceptionTest(){
        User user1 = new User("testId","testPW","testName","test@mail.com");
        User user2 = new User("testId","testPW2","testName2","test2@mail.com");
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                ()->userService.join(user2));
        assertThat(e.getMessage()).isEqualTo("중복된 아이디입니다. 다른 아이디를 사용해주세요.");
    }

}
