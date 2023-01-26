package db;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class UserRepository {

    private static final Database DATABASE = Database.getInstance();

    public static void addUser(User user) {
        String SQL = "insert into users(userId, password, name, email) values(?, ?, ?, ?)";
        try (Connection conn = DATABASE.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User findUserById(String userId) {
        String SQL = "select * from users where userId = ?";
        try (Connection conn = DATABASE.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, userId);
            User user = null;
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("userId");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");
                user = User.from(id, password, name, email);
            }
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection<User> findAll() {
        String SQL = "select * from users";
        try (Connection conn = DATABASE.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();
            Collection<User> users = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("userId");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");
                users.add(User.from(id, password, name, email));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
