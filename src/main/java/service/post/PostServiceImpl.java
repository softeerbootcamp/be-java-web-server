package service.post;

import db.post.PostDatabase;
import model.Post;

import java.util.Collection;
import java.util.Map;

public class PostServiceImpl implements PostService {
    private static PostService postService;
    private final PostDatabase postDatabase;

    private PostServiceImpl(PostDatabase postDatabase) {
        this.postDatabase = postDatabase;
    }

    public static PostService getInstance(PostDatabase postDatabase) {
        if (postService == null) {
            postService = new PostServiceImpl(postDatabase);
        }
        return postService;
    }


    @Override
    public void createPost(String userId, Map<String, String> params) {
        String title = params.get("title");
        String content = params.get("contents");
        postDatabase.addPost(Post.create(title, content, userId));
    }

    @Override
    public Collection<Post> findAll() {
        return postDatabase.findAll();
    }
}
