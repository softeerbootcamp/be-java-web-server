package service;

import model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.DBPostRepo;

import java.util.Map;

public class PostService {
    private static Logger logger = LoggerFactory.getLogger(PostService.class);

    public static Long createPost(Map<String, String> body) {
        String writer = body.get("writer");
        String title = body.get("title");
        String content = body.get("contents");
        logger.debug("writer : {}", body.get("writer"));
        logger.debug("title : {}", body.get("title"));
        logger.debug("contents : {}", body.get("contents"));

        Post post = DBPostRepo.get().addPost(new Post(writer, title, content));

        logger.debug("postID : {}", post.getPostId());
        return post.getPostId();
    }
}
