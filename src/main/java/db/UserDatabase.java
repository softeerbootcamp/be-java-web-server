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
            //users.put(user.getUserId(), user);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public static User findUserById(String userId) {
        try {
            Connection connection = DbConnectionManager.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from user where userId = ?;");

            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            User user = User.of(resultSet.getString("userId"), resultSet.getString("password"),
                    resultSet.getString("name"), resultSet.getString("email"));

            logger.debug("User [userId: {}, password: {}, name: {}, email: {}]",
                    user.getUserId(), user.getPassword(), user.getName(), user.getEmail());

            preparedStatement.close();
            connection.close();

            return user;
            //return users.get(userId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
