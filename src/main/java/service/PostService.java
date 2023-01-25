package service;

import db.Database;
import dto.PostCreateDTO;
import model.Post;
import model.User;

public class PostService {

    public Post create(PostCreateDTO postInfo, User user) {
        validate(postInfo);
        Post post = Post.of(user, postInfo);
        Database.savePost(Post.of(user, postInfo));
        return post;
    }

    private void validate(PostCreateDTO postInfo) {
        if (postInfo.isEmpty()) {
            throw new IllegalStateException("post has empty input");
        }
    }
}
