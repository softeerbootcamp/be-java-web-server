package model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import model.Board;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class BoardDto {

    private Integer boardId;
    private String writerId;
    private String writerName;
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    public Board toEntity(){
        return Board.builder()
                .contents(contents)
                .title(title)
                .build();
    }

    public static BoardDto from(Map<String, String> map){   //factory method only for reflection
        return BoardDto.builder()
                .contents(map.get("contents"))
                .title(map.get("title"))
                .build();
    }
}
