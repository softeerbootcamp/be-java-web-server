package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import model.dto.CommentDto;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class Comment {

    private Integer commentId;
    private String writerId;
    private String writerName;
    private String contents;

    private LocalDateTime createdAt;
    private Integer boardId;


    public void addMetadata(Integer boardId,String writer, String writerName, LocalDateTime createdAt){
        this.boardId = boardId;
        this.writerId = writer;
        this.writerName = writerName;
        this.createdAt = createdAt;
    }

    public CommentDto toDto(){
        return CommentDto.builder().writerId(writerId)
                .writerName(writerName)
                .contents(contents)
                .createdAt(createdAt)
                .boardId(boardId)
                .build();
    }

}
