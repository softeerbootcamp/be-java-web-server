package db;

import exception.DBException;

import java.sql.*;

public class DBUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/webserver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "zxcasdqwe123";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static void close(Connection con, PreparedStatement pstm, ResultSet rs) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new DBException(e);
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                throw new DBException(e);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DBException(e);
            }
        }
    }
}
