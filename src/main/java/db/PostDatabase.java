package db;

import model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostDatabase {
    private static final Logger logger = LoggerFactory.getLogger(PostDatabase.class);

    private static final PostDatabase instance;

    private PostDatabase() {}

    static {
        instance = new PostDatabase();
    }

    public static PostDatabase getInstance() {
        return instance;
    }

    public void add(Post post) throws SQLException {
        String query = "INSERT INTO Posts (title, content, userId, createdTime) VALUES(?, ?, ?, ?)";
        String[] args = new String[4];
        args[0] = post.getTitle();
        args[1] = post.getContent();
        args[2] = post.getUserId();
        args[3] = post.getCreatedTime().toString();

        QueryExecutor.executeUpdateQuery(query, args);
    }

    public List<Post> findAll() throws SQLException {
        String query = "SELECT * FROM Posts";
        String[] args = new String[0];

        List<Map<String, String>> result = QueryExecutor.executeSelectQuery(query, args);
        List<Post> postList = new ArrayList<>();

        for(Map<String, String> rs : result) {
            String id = rs.get("id");
            String title = rs.get("title");
            String content = rs.get("content");
            String userId = rs.get("userId");
            LocalDateTime createdTime = LocalDateTime.parse(rs.get("createdTime"));
            postList.add(Post.of(Integer.parseInt(id), title, content, userId, createdTime));
        }

        return postList;
    }
}
