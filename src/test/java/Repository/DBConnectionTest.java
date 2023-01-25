package Repository;


import model.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.DBConnectionManager;
import repository.DBPostRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class DBConnectionTest {

    private static final Logger logger = LoggerFactory.getLogger(DBConnectionTest.class);

    @Test
    @DisplayName("DB Manager를 이용한 테스트")
    void insertion_test() {
        //given
        String[] args = {"0", "0", "0", "0"};

        //when
        DBConnectionManager.sendSql("insert into Post(id, author, title, contents) values (?, ?, ?, ?)", args);

        //then
        List<List<String>> result = DBConnectionManager.sendSql("select LAST_INSERT_ID() from Post", null);
        String[] id = {result.get(0).get(0)};
        logger.info("inserted id was {}", id);
        assertNotEquals(result.get(0).get(0), "0");
        DBConnectionManager.sendSql("delete from Post where id=?", id);
    }

    @Test
    @DisplayName("Post Repository Level 테스트")
    void insertion_test_2() {
        //given
        Post post = DBPostRepo.get().addPost(new Post("123", "456", "789"));
        Post new_post = DBPostRepo.get().findPostById(post.getPostId()).get();
        assertEquals(post.toString(), new_post.toString());
        DBPostRepo.get().delete(post);
    }
}
