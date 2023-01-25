package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PostService {
    Logger logger = LoggerFactory.getLogger(PostService.class);

    public Long createPost(Map<String, String> body) {
        logger.debug("writer : {}", body.get("writer"));
        logger.debug("title : {}", body.get("title"));
        logger.debug("contents : {}", body.get("contents"));


        return null;
    }
}
