package Repository;


import model.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.DBConnectionManager;
import repository.PostRepo;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class DBConnectionTest {

    private static final Logger logger = LoggerFactory.getLogger(DBConnectionTest.class);

    @Test
    @DisplayName("DB Manager를 이용한 post 삽입 삭제 테스트")
    void insertion_test() {
        //given
        String[] args = {"0", "0", "0"};

        //when
        DBConnectionManager.sendSql("insert into Post(author, title, contents) values (?, ?, ?)", args);

        //then
        List<Map<String, String>> result = DBConnectionManager.sendSql("SELECT MAX(id) as id FROM Post", null);
        String[] id = {result.get(0).get("id")};
        logger.info("inserted id was {}", id);
        DBConnectionManager.sendSql("delete from Post where id=?", id);
        assertNotEquals(result.get(0).get("id"), "0");
    }

    @Test
    @DisplayName("Post Repository를 이용한 post 삽입 삭제 테스트")
    void insertion_test_2() {
        //given
        Post post = PostRepo.get().addPost(new Post("123", "456", "789"));
        Post new_post = PostRepo.get().findPostById(post.getPostId()).get();
        PostRepo.get().delete(post);
        assertEquals(post.toString(), new_post.toString());
    }
}
