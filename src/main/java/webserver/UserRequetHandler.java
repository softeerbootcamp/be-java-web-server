package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRequetHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserRequetHandler.class);

    public void createUser(String url) {
        String[] splited = url.split("\\?");
        String userInfo = splited[1];

        logger.debug("userInfo : {}" , userInfo);


    }
}
