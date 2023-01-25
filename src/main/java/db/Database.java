package db;

import com.google.common.collect.Maps;

import com.mysql.cj.protocol.Resultset;
import model.User;
import webserver.DatabaseConnHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        psmt.executeUpdate();
        conn.close();
        users.put(user.getUserId(), user);
    }
    // todo : findUserById 할때마다 커넥션 형성하는데, 비용이 너무 큰 느낌이다. 
    public static User findUserById(String userId) throws SQLException {
        User user = null;
        conn = databaseConnHandler.dbConnection();
        //SELECT * FROM User WHERE id = [id];
        String sql = "SELECT * FROM User WHERE id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1,userId);
        ResultSet rs = psmt.executeQuery();
        if (rs.next()) {
            user = new User(rs.getString("ID"),rs.getString("Password"),
                    rs.getString("Name"),rs.getString("Email") );
        }
        conn.close();

        return user;
    }

    public static Collection<User> findAll() {
        return users.values();
    }


}
