package db;

import model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.global.DBConnector;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDataBase {
    private static final Logger logger = LoggerFactory.getLogger(BoardDataBase.class);
    private static BoardDataBase boardDataBase;


    private BoardDataBase() {
    }

    public static BoardDataBase getInstance() {
        if (boardDataBase == null) {
            boardDataBase = new BoardDataBase();
        }
        return boardDataBase;
    }

    public void insert(Board board) {
        String sql = "INSERT into board (writer,title,content,create_time) VALUES ( ?, ?, ?, ?)";

        try (Connection connection = new DBConnector().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
        ) {
            pstmt.setString(1, URLDecoder.decode(board.getWriter(), StandardCharsets.UTF_8));
            pstmt.setString(2, URLDecoder.decode(board.getTitle(), StandardCharsets.UTF_8));
            pstmt.setString(3, URLDecoder.decode(board.getContent(), StandardCharsets.UTF_8));
            pstmt.setTimestamp(4, board.getCreateTime());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("[ERROR] BoardDatabase insertQuery에서의 에러 sql");
        }
    }

    public Board findById(Long boardId) {
        String sql = "SELECT * FROM Board b WHERE b.id=" + boardId;

        Board board = null;
        try (Connection connection = new DBConnector().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getString("id"));
                String writer = resultSet.getString("writer");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Timestamp createTime = resultSet.getTimestamp("create_time");
                board = new Board(id, writer, title, content, createTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("[ERROR] BoardDatabase selectQuery에서의 에러 sql: {}",sql);
        }

        return board;
    }

    /**
     * 기본 생성 날짜 내림차순 정렬
     */
    public List<Board> findAll() {
        List<Board> boardList = new ArrayList<>();
        String sql = "SELECT * FROM Board ORDER BY create_time DESC;";
        try (Connection connection = new DBConnector().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                Long id = Long.valueOf(resultSet.getString("id"));
                String writer = resultSet.getString("writer");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Timestamp createTime = resultSet.getTimestamp("create_time");
                boardList.add(new Board(id, writer, title, content, createTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("[ERROR] BoardDatabase selectQuery에서의 에러 sql");
        }

        return boardList;
    }


}
