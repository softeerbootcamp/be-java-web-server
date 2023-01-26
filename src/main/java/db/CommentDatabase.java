package db;

import com.google.common.collect.Maps;
import model.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CommentDatabase {

    public static final AtomicInteger commentId = new AtomicInteger();
    private CommentDatabase(){}

    public static CommentDatabase getInstance(){
        return CommentDatabase.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private final static CommentDatabase INSTANCE = new CommentDatabase();
    }
    private static Map<Integer, Comment> comments = Maps.newHashMap();

    public static void addComment(Comment comment) {
        comments.put(commentId.incrementAndGet(), comment);
    }

    public static Optional<Comment> findCommentById(int boardId) {
        return Optional.ofNullable(comments.get(boardId));
    }

    public static void deleteComment(String commentId){
        comments.remove(commentId);
    }

    public static List<Comment> findAll() {
        return new ArrayList<>(comments.values());
    }
}
