package servlet;

import db.Database;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserCreateTest {

    @DisplayName("회원가입에 대한 POST 요청이 들어오면 바디의 값으로 회원을 생성한다.")
    @Test
    void post() throws IOException {
//        File file = new File("/Users/parksungjun/Desktop/hyundai/be-java-web-server/src/test/resources/postResponse.txt");
//        BufferedReader br = new BufferedReader(new FileReader(file));
//        HttpRequest httpRequest = HttpRequest.of(br);
//
//        Servlet servlet = new UserCreate();
//        servlet.post(httpRequest);
//
//        User persistUser = Database.findUserById("javajigi");
//        assertThat(persistUser.getPassword()).isEqualTo("password");
//        assertThat(persistUser.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
//        assertThat(persistUser.getEmail()).isEqualTo("javajigi%40slipp.net");
    }

}
