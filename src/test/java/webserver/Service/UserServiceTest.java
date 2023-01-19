package webserver.Service;

import db.UserDatabase;
import model.User;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import webserver.exception.HttpRequestException;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    UserService userService;

    @BeforeEach
    void testSetUp(){
        userService = new UserService();
    }
    @Test
    @DisplayName("회원 가입 테스트_1명")
    void userCreateWithValidParameter(){

        //given
        UserDatabase userDatabase = mock(UserDatabase.class);
        String userId = "lee";
        String password = "password";
        String email = "test@test.com";
        String name = "yslee";
        User user = new User(userId, password, email, name);

        //when
        userService.addUser(userId, password, email, email);
        when(userDatabase.findUserById(Mockito.anyString())).thenReturn(Optional.empty());

        //then
        verify(userDatabase, times(1)).addUser(user);

    }

    @Test
    @DisplayName("회원 가입 테스트_2명")
    void userCreateWithValidParameter_twoUser(){

        //given
        UserDatabase userDatabase = mock(UserDatabase.class);
        String userId = "lee";
        String password = "password";
        String email = "test@test.com";
        String name = "yslee";
        User user = new User(userId, password, email, name);

        //when
        when(userDatabase.findUserById(Mockito.anyString())).thenReturn(Optional.empty());

        userService.addUser(userId, password, email, name);
        userService.addUser("anotherID", password, email,name);


        //then
        verify(userDatabase, times(2)).addUser(user);
    }

    @Test
    @DisplayName("회원 가입 테스트_중복 사용자")
    void userCreateWithValidParameter_DuplicateUser(){

        //given
        UserDatabase userDatabase = mock(UserDatabase.class);
        String userId = "lee";
        String password = "password";
        String email = "test@test.com";
        String name = "yslee";
        User user = new User(userId, password, email, name);

        //when
        when(userDatabase.findUserById(Mockito.anyString())).thenReturn(Optional.of(user));

        userService.addUser(userId, password, email, email);

        //then
        Assertions.assertThrows(HttpRequestException.class, () -> userService.addUser(userId, password, email, email));
    }


}