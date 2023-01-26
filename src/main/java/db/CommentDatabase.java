package db;

import dto.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommentDatabase {
    private static final Logger logger = LoggerFactory.getLogger(CommentDatabase.class);
    private static final Connection con = DBConnection.getInstance();

    public static void addComment(Comment c) {
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO comments VALUES (default, ?, ?, ?, ?)";  // default는 PK로, auto_increment
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, c.getBody());
            pstmt.setString(2, c.getUserId());
            pstmt.setString(3, c.getUserName());
            pstmt.setTimestamp(4, Timestamp.valueOf(c.getCreateDate()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection<Comment> getAll() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<>();

        try {
            String sql = "SELECT * FROM comments";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs != null && rs.next()) {
                comments.add(new Comment(rs.getInt("commentId"),
                        rs.getString("body"),
                        rs.getString("userId"),
                        rs.getString("userName"),
                        rs.getTimestamp("createDate").toLocalDateTime()));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return comments;
    }

    public static void clear() {
        PreparedStatement pstmt = null;

        try {
            String sql = "TRUNCATE comments";
            pstmt = con.prepareStatement(sql);

            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
