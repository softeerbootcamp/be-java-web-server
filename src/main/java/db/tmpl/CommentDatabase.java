package db.tmpl;

import model.Comment;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CommentDatabase {

    void addComment(Comment comment);

    Optional<Comment> findCommentById(int boardId);

    void deleteComment(String commentId);

    List<Comment> findAll();
}
