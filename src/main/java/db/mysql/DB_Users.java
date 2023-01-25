package db.mysql;

import model.User;

import java.sql.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DB_Users {

    public static User NOT_FOUND_USER = new User("", "", "" ,"");


    private static final String JDBC_URI = "jdbc:mysql://localhost/Java_was";
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PASSWORD = "admin1234";

    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    public static void addUser(User user) throws SQLException {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URI + "?" +
                    "user=" + ADMIN_ID + "&" +
                    "password=" + ADMIN_PASSWORD);

            String sql = "INSERT INTO Users VALUES (?, ?, ?, ?)";
            // uid, password, name, email
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());

            pstmt.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            pstmt.close();
            conn.close();
        }
    }

    public static User findUserById(String userId) throws SQLException {
        User foundUser = NOT_FOUND_USER;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URI + "?" +
                    "user=" + ADMIN_ID + "&" +
                    "password=" + ADMIN_PASSWORD);

            String sql = "SELECT * FROM Users where uid = ?";
            // uid, password, name, email
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();
            if(rs.next())
            {
                foundUser = new User(
                        rs.getString("uid"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            pstmt.close();
            conn.close();
            if(foundUser != NOT_FOUND_USER)
                return foundUser;
        }

        return NOT_FOUND_USER;
    }

    public static Collection<User> findAll() throws SQLException {
        Collection<User> allUsers = Collections.EMPTY_LIST;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URI + "?" +
                    "user=" + ADMIN_ID + "&" +
                    "password=" + ADMIN_PASSWORD);

            String sql = "SELECT * FROM Users";
            // uid, password, name, email
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while(rs.next())
            {
                allUsers.add(
                        new User(
                                rs.getString("uid"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email")
                        )
                );
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            pstmt.close();
            conn.close();
        }

        return allUsers;
    }
}
