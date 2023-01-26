package db.jdbc;

import db.tmpl.CommentDatabase;
import db.tmpl.ConnectionManager;
import model.Board;
import model.Comment;
import webserver.utils.CommonUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static webserver.utils.CommonUtils.LocalDateTimeToStr;
import static webserver.utils.CommonUtils.strToLocalDateTime;

public class JdbcCommentDatabase implements CommentDatabase {

    private ConnectionManager connectionManager;
    private JdbcCommentDatabase(){}

    public static JdbcCommentDatabase getInstance(){
        return JdbcCommentDatabase.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final JdbcCommentDatabase INSTANCE = new JdbcCommentDatabase();
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void addComment(Comment comment) {
       try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("insert into comment(writerId, writerName, contents, createdAt, boardId) values (?, ?, ?, ?, ?)")){
            ps.setString(1, comment.getWriterId());
            ps.setString(2, comment.getWriterName());
            ps.setString(3, comment.getContents());
            ps.setString(4, LocalDateTimeToStr(comment.getCreatedAt()));
            ps.setInt(5, comment.getBoardId());
            ps.executeUpdate();

       }catch (SQLException e) {
           throw new RuntimeException();
       }
    }

    @Override
    public Optional<Comment> findCommentById(int commentId) {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("select * from comment where commentId = ?")) {
            ps.setInt(1, commentId);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst())
                return Optional.empty();
            rs.next();
            Comment board = Comment.builder()
                    .commentId(rs.getInt("commentId"))
                    .writerId(rs.getString("writerId"))
                    .writerName(rs.getString("writerName"))
                    .contents(rs.getString("contents"))
                    .createdAt(strToLocalDateTime(rs.getString("createdAt")))
                    .boardId(rs.getInt("boardId"))
                    .build();
            return Optional.of(board);
        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteComment(String commentId) {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("delete from comment where commentId = ?")) {
            ps.setInt(1, Integer.parseInt(commentId));
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Comment> findAll() {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("select * from commentId")){
            List<Comment> boardList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Comment board = Comment.builder()
                        .commentId(rs.getInt("commentId"))
                        .writerId(rs.getString("writerId"))
                        .writerName(rs.getString("writerName"))
                        .contents(rs.getString("contents"))
                        .createdAt(strToLocalDateTime(rs.getString("createdAt")))
                        .boardId(rs.getInt("boardId"))
                        .build();
                boardList.add(board);}
            return boardList;
        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
