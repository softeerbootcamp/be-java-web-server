package webserver.Service;

import db.Database;
import model.User;
import org.junit.jupiter.api.*;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    AuthService authService;

    @BeforeEach
    void testSetUp(){

        authService = new AuthService();
    }
    @Test
    @DisplayName("회원 가입 테스트_1명")
    void userCreateWithValidParameter(){

        //given
        Database database = mock(Database.class);
        String userId = "lee";
        String password = "password";
        String email = "test@test.com";
        String name = "yslee";
        User user = new User(userId, password, email, name);

        //when
        authService.join(userId, password, email, email);

        //then
        verify(database, times(1)).addUser(user);

    }

    @Test
    @DisplayName("회원 가입 테스트_2명")
    void userCreateWithValidParameter_twoUser(){

        //given
        Database database = mock(Database.class);
        String userId = "lee";
        String password = "password";
        String email = "test@test.com";
        String name = "yslee";
        User user = new User(userId, password, email, name);

        //when
        authService.join(userId, password, email, name);
        authService.join("anotherID", password, email,name);

        //then
        verify(database, times(2)).addUser(user);
    }

    @Test
    @DisplayName("회원 가입 테스트_중복 사용자")
    void userCreateWithValidParameter_DuplicateUser(){

        //given
        Database database = mock(Database.class);
        String userId = "lee";
        String password = "password";
        String email = "test@test.com";
        String name = "yslee";
        User user = new User(userId, password, email, name);

        //when
        authService.join(userId, password, email, email);

        //then
        Assertions.assertThrows(HttpRequestException.class, () -> authService.join(userId, password, email, email));
        verify(database, times(2)).addUser(user);
    }


}