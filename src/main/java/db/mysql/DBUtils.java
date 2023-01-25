package db.mysql;

import java.sql.*;

public class DBUtils {

    private static final String JDBC_URI = "jdbc:mysql://localhost:0/Java_was";
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PASSWORD = "admin1234";

    private Connection conn = null;
    private PreparedStatement pstmt = null;

    public void openConn() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(JDBC_URI + "?" +
                "user=" + ADMIN_ID + "&" +
                "password=" + ADMIN_PASSWORD);
    }

    public void closeConn() throws SQLException {
        conn.close();
    }
}
