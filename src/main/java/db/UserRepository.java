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
        Connection connection = DATABASE.getConnection();
        String SQL = "insert into users(userId, password, name, email) values(?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(SQL);

        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.executeUpdate();

    }

    public static User findUserById(String userId) throws Exception {
        Connection connection = DATABASE.getConnection();
        String SQL = "select * from users where userId = ?";
        PreparedStatement pstmt = connection.prepareStatement(SQL);
        pstmt.setString(1, userId);
        ResultSet rs = pstmt.executeQuery();
        User user = null;
        while (rs.next()) {
            String id = rs.getString("userId");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String email = rs.getString("email");
            user = User.from(id, password, name, email);
        }

        return user;
    }

    public static Collection<User> findAll() throws Exception {
        Connection connection = DATABASE.getConnection();
        String SQL = "select * from users";
        PreparedStatement pstmt = connection.prepareStatement(SQL);
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
    }
}
