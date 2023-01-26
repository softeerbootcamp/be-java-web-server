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
        UserDAO userDAO = new UserDAO();
        User byUserId = userDAO.findByUserId(comment.getUserId());

        String sql = "INSERT INTO COMMENTS(uid, author, content) VALUES (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setLong(1, byUserId.getUid());
        pstmt.setString(2, comment.getAuthor());
        pstmt.setString(3, comment.getContent());

        pstmt.executeUpdate();
        logger.debug(">> comment {} Saved", comment.getUserId());
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

    public void deleteAll() throws SQLException {
        PreparedStatement pstmt1 = connection.prepareStatement("set FOREIGN_KEY_CHECKS = 0");
        pstmt1.executeUpdate();

        PreparedStatement pstmt2 = connection.prepareStatement("TRUNCATE TABLE comments");
        pstmt2.executeUpdate();

        PreparedStatement pstmt3 = connection.prepareStatement("set FOREIGN_KEY_CHECKS = 1");
        pstmt3.executeUpdate();
    }
}
