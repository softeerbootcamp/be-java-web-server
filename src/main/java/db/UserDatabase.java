package db;

import com.google.common.collect.Maps;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDatabase {
    private static final Logger logger = LoggerFactory.getLogger(UserDatabase.class);

    private static final UserDatabase instance;

    private UserDatabase() {}

    static {
        instance = new UserDatabase();
    }

    public static UserDatabase getInstance() {
        return instance;
    }

    public void addUser(User user) throws IllegalArgumentException, SQLException, NullPointerException {
        String query = "INSERT INTO Users (id, pwd, userName, email) VALUES(?, ?, ?, ?)";
        String[] args = new String[4];
        args[0] = user.getUserId();
        args[1] = user.getPassword();
        args[2] = user.getName();
        args[3] = user.getEmail();

        QueryExecutor.executeUpdateQuery(query, args);
    }

    public Optional<User> findUserById(String userId) throws SQLException, NullPointerException {
        String query = "SELECT * FROM Users WHERE id = ?";
        String[] args = new String[1];
        args[0] = userId;

        List<Map<String, String>> result = QueryExecutor.executeSelectQuery(query, args);

        if(result.size() == 0) {
            return Optional.empty();
        }

        String id = result.get(0).get("id");
        String pwd = result.get(0).get("pwd");
        String userName = result.get(0).get("userName");
        String email = result.get(0).get("email");

        return Optional.of(User.of(id, pwd, userName, email));
    }

    // TODO: 마저 적용하기
    public Optional<User> findUserByIdAndPwd(String userId, String pwd) throws SQLException, NullPointerException {
        Connection con = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM Users WHERE id = ? AND pwd = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, pwd);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                con.close();
                return Optional.empty();
            }

            String id = rs.getString("id");
            String password = rs.getString("pwd");
            String userName = rs.getString("userName");
            String email = rs.getString("email");

            con.close();
            return Optional.of(User.of(id, password, userName, email));
        } finally {
            con.close();
        }
    }

    public Collection<User> findAll() throws SQLException, NullPointerException {
        Connection con = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM Users";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            List<User> list = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                String pwd = rs.getString("pwd");
                String userName = rs.getString("userName");
                String email = rs.getString("email");

                User user = User.of(id, pwd, userName, email);
                list.add(user);
            }

            con.close();
            return list;
        } finally {
            con.close();
        }
    }
}
