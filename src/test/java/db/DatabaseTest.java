package db;

import model.User;
import org.junit.jupiter.api.Test;
import webserver.DatabaseConnHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void addUser() throws SQLException {
        DatabaseConnHandler databaseConnHandler = new DatabaseConnHandler();
        Connection conn;
        conn = databaseConnHandler.dbConnection();
        String sql = "insert into Users values(?,?,?,?)";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, "junmin");
        psmt.setString(2, "1234");
        psmt.setString(3, "유준민");
        psmt.setString(4, "junmin1623@naver.com");
        psmt.executeUpdate();
        psmt.close();
        conn.close();

    }
}