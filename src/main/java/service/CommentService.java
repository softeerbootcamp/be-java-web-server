package service;

import db.CommentDatabase;
import dto.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private static final Map<String, String> options = new HashMap<>();

    public static void createComment(String body, String userId, String userName) {
        Comment comment = new Comment(body, userId, userName);
        CommentDatabase.addComment(comment);
    }

    public static List<Comment> getAll() {
        return (List<Comment>) CommentDatabase.getAll();
    }
}
