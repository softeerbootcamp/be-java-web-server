package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import java.util.Map;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public String joinUser(String parseData) {
        Map<String, String> signInInfo = StringParser.dataParsing(parseData);

        for (Map.Entry<String, String> entry : signInInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            logger.debug("key: {}",key);
            logger.debug("value: {}",value);
        }

        User user = new User(signInInfo.get("userId"),
                signInInfo.get("password"),
                signInInfo.get("name"),
                signInInfo.get("email"));
        Database.addUser(user);

        return signInInfo.get("userId");
    }
}
