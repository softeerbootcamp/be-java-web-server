package bejavawebserver.repository;

import bejavawebserver.model.Qna;
import bejavawebserver.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcRepository.class);
    private final DataSource dataSource;

    public JdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean checkDuplicate(User user) {
        return findUserById(user.getUserId()) != null;
    }

    public void addUser(User user) {
        String sql = "insert into User values(?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public User findUserById(String userId) {
        String sql = "select * from User where user_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User findUser = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                findUser = new User(
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
            return findUser;

        } catch (Exception e) {
            logger.debug("error : {}", e.getMessage());
            return null;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public List<User> findUserAll() {
        String sql = "select * from User";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                User user = new User(
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                userList.add(user);
            }
            return userList;

        } catch (Exception e) {
            logger.debug("error : {}", e.getMessage());

            return null;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public List<Qna> findQnaAll() {
        String sql = "select * from qna";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Qna> qnaList = new ArrayList<>();
            while (rs.next()) {
                Qna qna = new Qna(
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getString("time")
                );
                qnaList.add(qna);
            }
            return qnaList;

        } catch (Exception e) {
            logger.debug("error : {}", e.getMessage());

            return null;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void addQna(Qna qna) {
        String sql = "insert into qna values(?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, qna.getWriter());
            pstmt.setString(2, qna.getContents());
            pstmt.setString(3, qna.getTime());
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        // 역순으로 닫아주어야 한다
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
