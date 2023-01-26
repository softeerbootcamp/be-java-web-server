package service.post;

import dao.PostDAO;
import dto.PostCreateDTO;
import model.Post;
import model.User;
import repository.PostRepository;
import repository.UserRepository;
import service.exception.EmptyInputException;
import service.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class PostService {

    private final PostRepository postRepository = new PostRepository();
    private final UserRepository userRepository = new UserRepository();

    public Post addNewPost(PostCreateDTO postInfo, User user) {
        validate(postInfo);
        Post post = Post.of(user, postInfo);
        postRepository.save(post);
        return post;
    }

    private void validate(PostCreateDTO postInfo) {
        if (postInfo.isEmpty()) {
            throw new EmptyInputException("post has empty input");
        }
    }

    public Post readPost(long postId) {
        PostDAO postDAO = postRepository.findById(postId).orElseThrow(() -> {
            throw new NotFoundException("post not found");
        });
        User user = userRepository.findById(postDAO.getUid()).orElseThrow(() -> {
            throw new NotFoundException("user not found");
        });
        return Post.of(user, postDAO);
    }

    public List<Post> getAllPost() {
        return postRepository.getAllPosts().stream()
                .map(postDAO -> {
                    User user = userRepository.findById(postDAO.getUid()).get();
                    return Post.of(user, postDAO);
                })
                .collect(Collectors.toList());
    }
}
