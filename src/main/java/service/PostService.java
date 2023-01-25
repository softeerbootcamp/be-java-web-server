package service;

import db.QueryBuilder;
import db.QueryBuilderFactory;
import http.exception.InternalServerErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Map<String, String>> getPosts() {
        List<Map<String, String>> posts = new ArrayList<>();
        try(QueryBuilder qb = QueryBuilderFactory.newQueryBuilder()) {
            ResultSet resultSet = qb.select("*").from("post").read();

            while(resultSet.next()) {
                posts.add(
                        Map.of(
                                "writer", resultSet.getString("writer"),
                                "title", resultSet.getString("title"),
                                "content", resultSet.getString("content"),
                                "date", resultSet.getString("date")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return posts;
    }
}
