package db;

import java.sql.*;

public class DBManager {
    private DBManager() {
    }

    private static class LazyHolder {
        public static final DBManager INSTANCE = new DBManager();
    }

    public static DBManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn;

        String url = "jdbc:mysql://localhost:3306/WAS?serverTimezone=UTC";
        String id = "root";
        String pw = "codesquad123";

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, id, pw);

        return conn;
    }

}
