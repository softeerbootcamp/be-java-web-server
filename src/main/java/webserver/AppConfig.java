package webserver;

import db.Database;
import db.MemoryDatabase;
import service.UserService;
import service.UserServiceImpl;

public class AppConfig {
    public UserService userService() {
        return new UserServiceImpl(makeDatabase());
    }

    private Database makeDatabase() {
        return new MemoryDatabase();
    }
}
