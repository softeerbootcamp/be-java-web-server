package db;

import model.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static model.general.Database.*;

public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    public void addUser(User user) {
        try {
            Connection connection =
                    DriverManager.getConnection(DB_URL.getDBInfo(), DB_USER_NAME.getDBInfo(), DB_PASSWORD.getDBInfo());

            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into user(userId, password, name, email) values(?, ?, ?, ?);");

            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());

            int changeRow = preparedStatement.executeUpdate();
            logger.debug("변경된 row 수(user): {}", changeRow);

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User findUserById(String userId) {
        User user = null;

        try {
            // DB Connection 정보 따로 관리하기 위해 Database라는 enum class 따로 생성, GitHub에 반영 X
            Connection connection =
                    DriverManager.getConnection(DB_URL.getDBInfo(), DB_USER_NAME.getDBInfo(), DB_PASSWORD.getDBInfo());

            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from user where userId = ?;");

            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            user = User.of(resultSet.getString("userId"), resultSet.getString("password"),
                    resultSet.getString("name"), resultSet.getString("email"));

            logger.debug("userId: {}, password: {}, name: {}, email: {}",
                    user.getUserId(), user.getPassword(), user.getName(), user.getEmail());

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public Collection<User> findAll() {
        Collection<User> users = new ArrayList<>();

        try {
            Connection connection =
                    DriverManager.getConnection(DB_URL.getDBInfo(), DB_USER_NAME.getDBInfo(), DB_PASSWORD.getDBInfo());

            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from user;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                users.add(User.of(resultSet.getString("userId"), resultSet.getString("password"),
                        resultSet.getString("name"), resultSet.getString("email")));
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void addMemo(String userId, String contents) {
        int userIdx = 0;

        try {
            Connection connection =
                    DriverManager.getConnection(DB_URL.getDBInfo(), DB_USER_NAME.getDBInfo(), DB_PASSWORD.getDBInfo());

            PreparedStatement preparedStatement =
                    connection.prepareStatement("select idx from user where userId = ?;");

            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            userIdx = resultSet.getInt("idx");

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection connection =
                    DriverManager.getConnection(DB_URL.getDBInfo(), DB_USER_NAME.getDBInfo(), DB_PASSWORD.getDBInfo());

            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into memo(userIdx, contents) values(?, ?);");

            preparedStatement.setInt(1, userIdx);
            preparedStatement.setString(2, contents);

            int changeRow = preparedStatement.executeUpdate();
            logger.debug("변경된 row 수(memo): {}", changeRow);

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> findAllMemos() {
        List<String> memos = new ArrayList<>();

        try {
            Connection connection =
                    DriverManager.getConnection(DB_URL.getDBInfo(), DB_USER_NAME.getDBInfo(), DB_PASSWORD.getDBInfo());

            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from memo;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                memos.add(resultSet.getString("contents"));
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memos;
    }
}
