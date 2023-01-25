package webserver;

import db.user.MemoryUserDatabase;
import db.user.MySqlUserDatabase;
import db.user.UserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import service.UserServiceImpl;

public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private static final String url = "jdbc:mysql://localhost:3306/user_data_db?serverTimezone=UTC";
    private static final String userId = "root";
    private static final String password = "12341234";
    private ConnectionPool connectionPool;

    public AppConfig() {
        logger.debug(url);
        connectionPool = new ConnectionPool(3, url, userId, password);
        Thread thread = new Thread(connectionPool);
        thread.start();
    }

    public UserService userService() {
        return UserServiceImpl.getInstance(createMySqlDatabase());
    }

    private UserDatabase createMemoryDatabase() {
        return MemoryUserDatabase.getInstance();
    }

    private UserDatabase createMySqlDatabase() {
        return new MySqlUserDatabase(connectionPool);
    }
}
