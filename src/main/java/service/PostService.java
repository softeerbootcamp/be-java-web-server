package service;

import db.PostDatabase;
import model.Post;

import java.sql.SQLException;
import java.util.List;

public class PostService {
    private static final PostService instance;

    private final PostDatabase postDatabase;

    static {
        instance = new PostService();
    }

    private PostService() {
        postDatabase = PostDatabase.getInstance();
    }

    public static PostService getInstance() {
        return instance;
    }

    public void addPost(Post post) throws SQLException {
        postDatabase.add(post);
    }

    public List<Post> getAllPosts() throws SQLException {
        return postDatabase.findAll();
    }
}
