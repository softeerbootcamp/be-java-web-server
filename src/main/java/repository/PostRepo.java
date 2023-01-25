package repository;

import model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepo {
    Post addPost(Post post);

    Optional<Post> findPostById(Long postId);

    Collection<Post> findAll();

    void delete(Post post);
}