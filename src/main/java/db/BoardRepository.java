package db;

import model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BoardRepository {

    private static final Logger logger = LoggerFactory.getLogger(BoardRepository.class);

    public BoardRepository() {
    }

    public void addBoard(String createdDate, String author, String content) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String query = "INSERT INTO WAS.BOARD(createdDate,author,content) VALUES (?,?,?)";

        try {
            conn = DBManager.getInstance().getConnection();

            logger.info("Connection 객체 생성성공");

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, createdDate);
            pstmt.setString(2, author);
            pstmt.setString(3, content);

            logger.info(pstmt.toString());

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

    public Collection<Board> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT * FROM WAS.BOARD ORDER BY createdDate DESC LIMIT 10";

        try {
            conn = DBManager.getInstance().getConnection();

            logger.info("Connection 객체 생성성공");

            pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                List<Board> boardList = new ArrayList<>();
                do {
                    Board board = Board.of(
                            rs.getString("createdDate"),
                            rs.getString("author"),
                            rs.getString("content"));

                    boardList.add(board);

                } while (rs.next());
                return boardList;
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
