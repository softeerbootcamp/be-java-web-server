package db;

import model.User;
import model.request.UserCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    private final Connection connection;

    public UserDAO() {
        try {
            connection = Database.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(UserCreate userCreate) throws SQLException {
        String sql = "INSERT INTO USERS(userId, password, name, email) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, userCreate.getUserId());
        pstmt.setString(2, userCreate.getPassword());
        pstmt.setString(3, userCreate.getName());
        pstmt.setString(4, userCreate.getEmail());
        logger.debug(">> sql : {}", sql);

        pstmt.executeUpdate();
        logger.debug(">> User {} Saved", userCreate.getUserId());
    }

    public User findByUserId(String userId) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM users WHERE userId = ?");
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new User(
                    resultSet.getString("uid"),
                    resultSet.getString("userId"),
                    resultSet.getString("password"),
                    resultSet.getString("name"),
                    resultSet.getString("email"));
        }
        return null;
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getString("uid"),
                    resultSet.getString("userId"),
                    resultSet.getString("password"),
                    resultSet.getString("name"),
                    resultSet.getString("email"));
            users.add(user);
        }
        return users;
    }

    public void deleteAll() throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("TRUNCATE TABLE users");
        pstmt.executeUpdate();
    }
}
