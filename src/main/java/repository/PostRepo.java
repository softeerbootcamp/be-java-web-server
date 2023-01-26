package repository;

import model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepo {
    PostRepo instance = new DBPostRepo();

    static PostRepo get() {
        return instance;
    }

    Post addPost(Post post);

    Optional<Post> findPostById(Long postId);

    Collection<Post> findAll();

    void delete(Post post);
}