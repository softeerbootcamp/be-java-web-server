package util;

import Request.HttpRequest;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ManageDBTest {
    private final Logger logger = LoggerFactory.getLogger(ManageDBTest.class);
    @Test
    public void saveUserTest(){
        //given

        User user = new User("jieon","1234","kim","kim@naver.com");
        InputStream in = new ByteArrayInputStream("GET /user/create?userId=jieon&password=1234&name=kim&email=kim%40naver.com HTTP/1.1".getBytes());
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            HttpRequest httpRequest = HttpRequest.createReqeust(br);
        //when
            ManageDB.saveUser(httpRequest);

        //then
            User result = ManageDB.findUserById("jieon");
            logger.debug("test: "+result.toString());
            Assertions.assertThat(result.getUserId()).isEqualTo(user.getUserId());
            Assertions.assertThat(result.getName()).isEqualTo(user.getName());
            Assertions.assertThat(result.getPassword()).isEqualTo(user.getPassword());
            Assertions.assertThat(result.getEmail()).isEqualTo(user.getEmail());

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
