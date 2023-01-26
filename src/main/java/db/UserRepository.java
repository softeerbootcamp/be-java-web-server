package db;

import exception.UserNotFoundException;
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
        Connection conn = null;
        PreparedStatement pstmt = null;

        String query = "INSERT INTO WAS.user VALUES (?,?,?,?)";


        try {
            conn = DBManager.getInstance().getConnection();

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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public User findUserById(String userId) throws UserNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT * FROM WAS.USER WHERE UID = ?";


        try {
            conn = DBManager.getInstance().getConnection();

            logger.info("Connection 객체 생성성공");

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return User.of(
                        rs.getString("uid"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }

        } catch (ClassNotFoundException e) {
            logger.error("드라이버 로드 실패");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserNotFoundException();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Collection<User> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT * FROM WAS.USER";

        try {
            conn = DBManager.getInstance().getConnection();

            logger.info("Connection 객체 생성성공");

            pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                List<User> userList = new ArrayList<>();
                do {
                    User user = User.of(
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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void deleteById(String userId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String query = "DELETE FROM WAS.USER WHERE UID = ?";


        try {
            conn = DBManager.getInstance().getConnection();

            logger.info("Connection 객체 생성성공");

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            logger.error("드라이버 로드 실패");
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
