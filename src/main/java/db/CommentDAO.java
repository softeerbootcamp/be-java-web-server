package db;

import model.Comment;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommentDAO {
    private static final Logger logger = LoggerFactory.getLogger(CommentDAO.class);

    private final Connection connection;

    public CommentDAO() {
        try {
            connection = Database.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Comment comment) throws SQLException {
        String sql = "INSERT INTO COMMENTS(author, content) VALUES (?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setString(1, comment.getAuthor());
        pstmt.setString(2, comment.getContent());

        pstmt.executeUpdate();
        logger.debug(">> comment {} Saved", comment.getAuthor());
    }
}
