package util;

import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HtmlBuildUtilTest {
    private final Logger logger = LoggerFactory.getLogger(HtmlBuildUtilTest.class);
    @Test
    public void userListTest(){
        List<User> users = new ArrayList<>();
        users.add(new User("jieon", "123", "jello", "hello@gmail.com"));
        String s = HtmlBuildUtil.buildUserList(users);
        logger.debug(s);
    }
}
