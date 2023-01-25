package db.user;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlUserDatabase implements UserDatabase {
    private static final Logger logger = LoggerFactory.getLogger(MySqlUserDatabase.class);
    private final ConnectionPool connectionPool;

    public MySqlUserDatabase(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void addUser(User user) {
        try (Connection connection = connectionPool.getConnection()) {
            String insertSQL = "INSERT INTO users VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
            int count = preparedStatement.executeUpdate();
            if (count == 1)
                logger.debug("데이터 입력 성공");
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findUserById(String userId) {
        try (Connection connection = connectionPool.getConnection()) {
            String selectSQL = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    return new User(userId, password, name, email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<User> findAll() {
        try (Connection connection = connectionPool.getConnection()) {
            Collection<User> users = new ArrayList<>();
            String selectSQL = "SELECT * FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String userId = resultSet.getString("user_id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    users.add(new User(userId, password, name, email));
                }
                return users;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
