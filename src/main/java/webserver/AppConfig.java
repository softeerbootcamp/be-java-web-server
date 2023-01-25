package webserver;

import db.UserDatabase;
import db.MemoryUserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import service.UserServiceImpl;

public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    public UserService userService() {
        return UserServiceImpl.getInstance(makeDatabase());
    }

    private UserDatabase makeDatabase() {
        return MemoryUserDatabase.getInstance();
    }
}
