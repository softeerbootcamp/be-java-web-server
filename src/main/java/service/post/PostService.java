package service.post;

import model.Post;

import java.util.Collection;
import java.util.Map;

public interface PostService {
    void createPost(String userId, Map<String, String> params);

    Collection<Post> findAll();
}
