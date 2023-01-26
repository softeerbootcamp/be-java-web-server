package db;

import model.Comment;
import model.User;
import model.response.CommentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<CommentResponse> findAll() throws SQLException {
        List<CommentResponse> comments = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM comments");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            CommentResponse comment = new CommentResponse(resultSet.getString("author"),
                    resultSet.getString("content"),
                    resultSet.getDate("create_date").toLocalDate());
            comments.add(comment);
        }
        return comments;
    }
}
