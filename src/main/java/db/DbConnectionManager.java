package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(DbConnectionManager.class);

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(DbConnection.URL,
                    DbConnection.USER, DbConnection.PASSWORD);
            logger.debug("DB Connection Success!");
            return conn;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
