package db.jdbc;

import db.tmpl.ConnectionManager;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionManager implements ConnectionManager {
    private final String USER_NAME = "root";
    private final String PASSWORD = "1234";
    private final String DATABASE_NAME = "user";

    private MySQLConnectionManager(){}

    public static MySQLConnectionManager getInstance(){
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final MySQLConnectionManager INSTANCE  = new MySQLConnectionManager();
    }

    @Override
    public Connection makeConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE_NAME, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
