package db.memoryDB;

import com.google.common.collect.Maps;
import db.tmpl.CommentDatabase;
import model.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryCommentDatabase implements CommentDatabase {

    private static Map<Integer, Comment> comments = Maps.newHashMap();
    public static final AtomicInteger commentId = new AtomicInteger();
    private MemoryCommentDatabase(){}

    public static MemoryCommentDatabase getInstance(){
        return MemoryCommentDatabase.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private final static MemoryCommentDatabase INSTANCE = new MemoryCommentDatabase();
    }

   @Override
   public void addComment(Comment comment) {
        comments.put(commentId.incrementAndGet(), comment);
    }

    @Override
    public Optional<Comment> findCommentById(int boardId) {
        return Optional.ofNullable(comments.get(boardId));
    }

    @Override
    public void deleteComment(String commentId){
        comments.remove(commentId);
    }

    @Override
    public List<Comment> findAll() {
        return new ArrayList<>(comments.values());
    }
}
