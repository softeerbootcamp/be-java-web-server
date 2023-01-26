package model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import model.Comment;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class CommentDto {

    private String writerId;
    private String writerName;
    private String contents;

    private LocalDateTime createdAt;
    private Integer boardId;

    public void addMetadata(Integer boardId, String writer, String writerName, LocalDateTime createdAt){
        this.boardId = boardId;
        this.writerId = writer;
        this.writerName = writerName;
        this.createdAt = createdAt;
    }

    public static CommentDto from(Map<String, String> map){   //factory method only for reflection
        return CommentDto.builder()
                .contents(map.get("contents"))
                .build();
    }

    public Comment toEntity(){
        return Comment.builder().contents(contents).build();
    }
}
