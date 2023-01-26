package db.post;

import model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class MySqlPostDatabase implements PostDatabase {
    private static final Logger logger = LoggerFactory.getLogger(MySqlPostDatabase.class);
    private final ConnectionPool connectionPool;

    public MySqlPostDatabase(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Collection<Post> findAll() {
        try {
            Connection connection = connectionPool.getConnection();
            Collection<Post> posts = new ArrayList<>();
            String selectSQL = "SELECT * FROM posts";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String title = resultSet.getString("title");
                        String content = resultSet.getString("content");
                        String writer = resultSet.getString("writer");
                        String dateTime = resultSet.getString("dt_created");
                        posts.add(Post.createWithDT(title, content, writer, dateTime));
                    }
                    return posts.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void addPost(Post post) {
        try {
            Connection connection = connectionPool.getConnection();
            String insertSQL = "INSERT INTO posts (title, content, writer, dt_created) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, post.getTitle());
                preparedStatement.setString(2, post.getContent());
                preparedStatement.setString(3, post.getWriter());
                preparedStatement.setString(4, post.getDateTime());
                int count = preparedStatement.executeUpdate();
                if (count == 1)
                    logger.debug("데이터 입력 성공");
            } finally {
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
