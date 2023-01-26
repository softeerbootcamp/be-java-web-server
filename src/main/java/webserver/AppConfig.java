package webserver;

import db.session.MySqlSessionDatabase;
import db.session.SessionDatabase;
import db.user.MySqlUserDatabase;
import db.user.UserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.session.SessionService;
import service.session.SessionServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private static final String url = "jdbc:mysql://localhost:3306/user_data_db?serverTimezone=UTC";
    private static final String userId = "root";
    private static final String password = "12341234";
    private ConnectionPool connectionPool;

    public AppConfig() {
        connectionPool = new ConnectionPool(10, url, userId, password);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(sessionService()::checkExpiredSessions, 0, 5, TimeUnit.SECONDS);
        Thread thread = new Thread(connectionPool);
        thread.start();
    }

    public UserService userService() {
        return UserServiceImpl.getInstance(createMySqlUserDatabase());
    }

    public SessionService sessionService() {
        return SessionServiceImpl.getInstance(createMySqlSessionDatabase());
    }


    private UserDatabase createMySqlUserDatabase() {
        return new MySqlUserDatabase(connectionPool);
    }

    private SessionDatabase createMySqlSessionDatabase() {
        return new MySqlSessionDatabase(connectionPool);
    }
}
