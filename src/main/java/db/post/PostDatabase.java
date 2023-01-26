package db.post;

import model.Post;

import java.util.Collection;

public interface PostDatabase {
    void addPost(Post post);

    Collection<Post> findAll();
}
