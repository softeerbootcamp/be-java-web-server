package db;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public void addUser(User user) throws SQLIntegrityConstraintViolationException {
        String url = "jdbc:mysql://localhost:3306/WAS?serverTimezone=UTC";
        String id = "root";
        String pw = "codesquad123";
        Connection conn = null;
        PreparedStatement pstmt = null;

        String query = "INSERT INTO WAS.user VALUES (?,?,?,?)";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            logger.info("Connection 객체 생성성공");

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            logger.error("드라이버 로드 실패");
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) conn.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public User findUserById(String userId) {
        String url = "jdbc:mysql://localhost:3306/WAS?serverTimezone=UTC";
        String id = "root";
        String pw = "codesquad123";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT * FROM WAS.USER WHERE UID = ?";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            logger.info("Connection 객체 생성성공");

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("uid"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }

        } catch (ClassNotFoundException e) {
            logger.error("드라이버 로드 실패");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Collection<User> findAll() {
        String url = "jdbc:mysql://localhost:3306/WAS?serverTimezone=UTC";
        String id = "root";
        String pw = "codesquad123";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT * FROM WAS.USER";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            logger.info("Connection 객체 생성성공");

            pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                List<User> userList = new ArrayList<>();
                do {
                    User user = new User(
                            rs.getString("uid"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("email"));

                    userList.add(user);

                } while (rs.next());
                return userList;
            }

        } catch (ClassNotFoundException e) {
            logger.error("드라이버 로드 실패");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

}
