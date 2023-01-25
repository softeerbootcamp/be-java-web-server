package service;

import db.QueryBuilder;
import db.QueryBuilderFactory;
import http.exception.InternalServerErrorException;

import java.time.LocalDateTime;

public class PostService {
    private final QueryBuilder queryBuilder;

    public PostService() {
        this.queryBuilder = QueryBuilderFactory.newQueryBuilder();
    }

    public void create(String writer, String title, String content) {
        boolean result = queryBuilder
                .insert(writer, title, content, LocalDateTime.now().toString())
                .into("post")
                .fetch();

        if (!result) {
            throw new InternalServerErrorException("서버에서 에러가 발생하였습니다.");
        }
    }
}
