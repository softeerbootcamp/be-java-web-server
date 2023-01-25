package db;

import model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.global.DBConnector;
import view.UserRender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            DBConnector dbConnector = new DBConnector();
            connection = dbConnector.getConnection();
            pstmt = null;

            String sql = "INSERT into board (writer,title,content,create_time) VALUES ( ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, board.getWriter());
            pstmt.setString(2, board.getTitle());
            pstmt.setString(3, board.getContent());
            pstmt.setTimestamp(4, board.getCreateTime());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("[ERROR] BoardDatabase insertQuery에서의 에러 sql:{}",pstmt.toString());
        } finally {
            closeConnection(connection, pstmt);
        }
    }

    private void closeConnection(Connection connection, PreparedStatement pstmt)  {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }  catch (SQLException e) {
            e.printStackTrace();
            logger.error("close DB Connection 중 에러");
        }

    }
}
