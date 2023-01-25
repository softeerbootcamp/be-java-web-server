package db;

import com.google.common.collect.Maps;

import model.User;
import webserver.DatabaseConnHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();
    private static DatabaseConnHandler databaseConnHandler;
    private static Connection conn;
    public static void addUser(User user) throws SQLException {
        conn = databaseConnHandler.dbConnection();
        String sql = "insert into User values(?,?,?,?)";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1,user.getUserId());
        psmt.setString(2,user.getPassword());
        psmt.setString(3,user.getName());
        psmt.setString(4,user.getEmail());
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }


}
