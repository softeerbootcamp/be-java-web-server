package db;

import model.User;
import service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDatabase {
    private static final Connection con = DBConnection.getInstance();

    public static void addUser(User user) {
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO users VALUES (default, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getEmail());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<User> findUserById(String userId) {
        PreparedStatement pstmt = null;
        ResultSet result = null;
        User user = null;

        try {
            String sql = "SELECT * FROM users WHERE userId=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            result = pstmt.executeQuery();

            if(result == null || !result.next())
                return Optional.empty();

            user = User.UserBuilder.builder()
                    .setUserId(result.getString("userId"))
                    .setName(result.getString("name"))
                    .setPassword(result.getString("password"))
                    .setEmail(result.getString("email"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(user);
    }

    public static Optional<Collection<User>> getAll() {
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<User> users = new ArrayList<>();

        try {
            String sql = "SELECT * FROM users";
            pstmt = con.prepareStatement(sql);

            result = pstmt.executeQuery();

            while(result != null && result.next()) {
                User user = User.UserBuilder.builder()
                        .setUserId(result.getString("userId"))
                        .setName(result.getString("name"))
                        .setEmail(result.getString("email"))
                        .build();
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(users);
    }

    public static void clear() {
        PreparedStatement pstmt = null;

        try {
            String sql = "TRUNCATE users";
            pstmt = con.prepareStatement(sql);

            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
