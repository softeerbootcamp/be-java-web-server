package db;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class UserRepository {

    private static final Database DATABASE = Database.getInstance();

    public static void addUser(User user) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DATABASE.getConnection();
            String SQL = "insert into users(userId, password, name, email) values(?, ?, ?, ?)";
            pstmt = conn.prepareStatement(SQL);

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.executeUpdate();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static User findUserById(String userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        User user = null;
        try {
            conn = DATABASE.getConnection();
            String SQL = "select * from users where userId = ?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("userId");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");
                user = User.from(id, password, name, email);
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }

    public static Collection<User> findAll() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Collection<User> users = new ArrayList<>();
        try {
            conn = DATABASE.getConnection();
            String SQL = "select * from users";
            pstmt = conn.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("userId");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");
                users.add(User.from(id, password, name, email));
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return users;
    }
}
