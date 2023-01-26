package db;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class UserDatabase {

    private static final Logger logger = LoggerFactory.getLogger(UserDatabase.class);

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
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public static User findUserById(String userId) {
        User user = null;

        try {
            Connection connection = DbConnectionManager.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from user where userId = ?;");

            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            user = User.of(resultSet.getString("userId"), resultSet.getString("password"),
                    resultSet.getString("name"), resultSet.getString("email"));

            logger.debug("User [userId: {}, password: {}, name: {}, email: {}]",
                    user.getUserId(), user.getPassword(), user.getName(), user.getEmail());

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return user;
    }

    public static Collection<User> findAll() {
        Collection<User> users = new ArrayList<>();

        try {
            Connection connection = DbConnectionManager.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from user;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(User.of(resultSet.getString("userId"), resultSet.getString("password"),
                        resultSet.getString("name"), resultSet.getString("email")));
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return users;
    }
}
