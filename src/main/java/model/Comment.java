package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Comment {

    private Integer diaryId;
    private String writer;
    private String contents;

    private LocalDateTime createdAt;
    private Integer boardId;


}
