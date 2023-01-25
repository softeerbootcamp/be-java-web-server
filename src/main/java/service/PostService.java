package service;

import db.QueryBuilder;
import db.QueryBuilderFactory;
import http.exception.InternalServerErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostService {
    public void create(String writer, String title, String content) {
        boolean result;
        try(QueryBuilder queryBuilder = QueryBuilderFactory.newQueryBuilder()) {
            result = queryBuilder
                    .insert(writer, title, content, LocalDateTime.now().toString())
                    .into("post")
                    .fetch();
        }

        if (!result) {
            throw new InternalServerErrorException("서버에서 에러가 발생하였습니다.");
        }
    }

    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        try(QueryBuilder qb = QueryBuilderFactory.newQueryBuilder()) {
            ResultSet resultSet = qb.select("*").from("post").read();

            while(resultSet.next()) {
                posts.add(
                        new Post(
                                1L,
                                resultSet.getString("writer"),
                                resultSet.getString("title"),
                                resultSet.getString("content"),
                                resultSet.getString("date")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return posts;
    }
}
