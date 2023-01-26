package webserver.Service;

import db.memoryDB.MemoryUserDatabase;
import db.tmpl.UserDatabase;
import model.User;
import model.dto.UserDto;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

public class UserService {

    private UserDatabase userDatabase;
    private UserService (){}

    public static UserService getInstance(){
        return UserService.LazyHolder.INSTANCE;
    }

    public void setUserDatabase(UserDatabase userDataBase) {
        this.userDatabase = userDataBase;
    }

    private static class LazyHolder{   //Singleton
        private static final UserService INSTANCE = new UserService();
    }

    public byte[] addUser(UserDto newUser) {
        userDatabase.findUserById(newUser.getUserId()).ifPresent(m -> {
            throw HttpRequestException.builder()
                    .statusCode(StatusCodes.BAD_REQUEST)
                    .msg("<script>alert('사용자가 이미 존재합니다'); history.go(-1);</script>")
                    .build();
        });
        userDatabase.addUser(User.from(newUser));
        return newUser.getUserId().getBytes();
    }

    public User login(String userId, String password) {
        User user = userDatabase.findUserById(userId).orElseThrow(() ->
                HttpRequestException.builder()
                            .statusCode(StatusCodes.BAD_REQUEST)
                            .msg("<script>alert('존재하지 않는 계정입니다'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>")
                            .build()); //wrong ID
        if(!user.getPassword().equals(password))  //with wrong password
                throw HttpRequestException.builder()
                    .statusCode(StatusCodes.BAD_REQUEST)
                    .msg("<script>alert('패스워드가 올바르지 않습니다'); window.location.href = 'http://localhost:8080/user/login_failed.html';</script>")
                    .build();
        return user; // with valid password
    }
}

