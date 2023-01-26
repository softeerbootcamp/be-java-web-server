package util;

import request.HttpRequest;
import db.SessionDb;
import model.Session;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class LoginUtilTest {
    private final Logger logger = LoggerFactory.getLogger(LoginUtilTest.class);
    @Test
    public void checkSessionTest() {
        //given
        User ex = new User("jieon", "123", "kk", "k@gmail.ocm");
        Session s = new Session(ex);
        logger.debug("session: "+s.toString());
        SessionDb.putSession(s);

        //when
        byte[] req = ("GET /user/list.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cookie: "+"sid="+s.getSessionId()+"\r\n").getBytes();
        InputStream in = new ByteArrayInputStream(req);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            HttpRequest httpRequest = HttpRequest.createReqeust(br);

            User user = LoginUtil.checkSession(httpRequest);
        //then
            logger.debug(user.toString());
            Assertions.assertThat(user).isNotNull();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
