package bejavawebserver.repository;

import bejavawebserver.model.QnaForm;
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
import java.util.*;

@Repository
public class JdbcRepository {
    private final DataSource dataSource;

    private static final Logger logger = LoggerFactory.getLogger(JdbcRepository.class);

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

        try{
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
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();
            while(rs.next()){
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

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<User> userList = new ArrayList<>();
            while(rs.next()){
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

    public List<QnaForm> findQnaAll() {
        String sql = "select * from qna";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<QnaForm> qnaList = new ArrayList<>();
            while(rs.next()){
                QnaForm qna = new QnaForm(
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents")
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

    public void addQna(QnaForm qnaForm) {
        String sql = "insert into qna values(?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, qnaForm.getWriter());
            pstmt.setString(2, qnaForm.getTitle());
            pstmt.setString(3, qnaForm.getContents());
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection(){
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        // 역순으로 닫아주어야 한다
        try{
            if(pstmt != null){
                pstmt.close();
            }
            if(conn != null){
                conn.close();
            }
            if(rs != null){
                rs.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }



}
