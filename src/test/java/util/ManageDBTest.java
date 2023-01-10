package util;

import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ManageDBTest {
    private final Logger logger = LoggerFactory.getLogger(ManageDBTest.class);
    @Test
    public void saveUserTest(){
        Map<String,String> querys = new HashMap<>(){
            {
                put("userId","jieon");
                put("password", "kdjfk");
                put("name","kim");
                put("email", "kim@naver.com");
            }
        };
        User user = new User(querys.get("userId"),querys.get("password"),querys.get("kim"),querys.get("email"));

        ManageDB.saveUser(querys);
        User result = ManageDB.findUserById("jieon");
        logger.debug("test: "+result.toString());

        Assertions.assertThat(result.equals(user));
    }
}
