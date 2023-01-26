package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import model.dto.BoardDto;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class Board {

    private Integer boardId;
    private String writerId;
    private String writerName;
    private String title;
    private String contents;
    private LocalDateTime createdAt;


    public void addMetadata(String writer, String writerName, LocalDateTime createdAt){
        this.writerId = writer;
        this.writerName = writerName;
        this.createdAt = createdAt;
    }

    public BoardDto toDto(){
        return new BoardDto(this.boardId, this.writerId, this.writerName, this.title, this.contents, this.createdAt);
    }

}
