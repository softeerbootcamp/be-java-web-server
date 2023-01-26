package service;

import Utility.PostValidation;
import exceptions.CustomException;
import model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.PostRepo;
import repository.SessionRepo;
import repository.UserRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private static final PostRepo postRepo = PostRepo.get();
    public static Long createPost(Map<String, String> body) {
        String writer = body.get("writer");
        String title = body.get("title");
        String content = body.get("contents");
        logger.debug("writer : {}", body.get("writer"));
        logger.debug("title : {}", body.get("title"));
        logger.debug("contents : {}", body.get("contents"));

        if (!PostValidation.isValid(writer, title, content)) {
            throw new CustomException("올바르지 않은 입력입니다.");
        }
        Post post = postRepo.addPost(new Post(writer, title, content));

        logger.debug("postID : {}", post.getPostId());
        return post.getPostId();
    }


    public static List<Post> findAll() {
        List<Post> result = new ArrayList<>(postRepo.findAll());
        Collections.reverse(result);
        return result;
    }
}
