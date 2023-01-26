package db;

import com.google.common.collect.Maps;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class UserDatabase {

    private static final Logger logger = LoggerFactory.getLogger(UserDatabase.class);
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        try {
            Connection conn = DbConnectionManager.getConnection();
            String sql = "insert into User values(?,?,?,?)";
            PreparedStatement psmt = conn.prepareStatement(sql);

            psmt.setString(1, user.getUserId());
            psmt.setString(2, user.getPassword());
            psmt.setString(3, user.getName());
            psmt.setString(4, user.getEmail());
            psmt.executeUpdate();

            psmt.close();
            conn.close();

            users.put(user.getUserId(), user);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
