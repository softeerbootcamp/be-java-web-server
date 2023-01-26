package db;

import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class SessionRepository {

    private static final Logger logger = LoggerFactory.getLogger(SessionRepository.class);

    public SessionRepository() {
    }

    public void addSession(String sessionId, String userId, String userName) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String query = "INSERT INTO WAS.SESSION VALUES (?,?,?)";


        try {
            conn = DBManager.getInstance().getConnection();

            logger.info("Connection 객체 생성성공");

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, sessionId);
            pstmt.setString(2, userId);
            pstmt.setString(3, userName);

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            logger.error("드라이버 로드 실패");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(pstmt != null) {
                try{
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Session findById(String sessionId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT * FROM WAS.SESSION WHERE sessionID = ?";
        try {
            conn = DBManager.getInstance().getConnection();

            logger.info("Connection 객체 생성성공");

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, sessionId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Session(
                        rs.getString("sessionId"),
                        rs.getString("userId"),
                        rs.getString("userName"));
            }

        } catch (ClassNotFoundException e) {
            logger.error("드라이버 로드 실패");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(pstmt != null) {
                try{
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
