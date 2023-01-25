package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.Board;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
public class BoardDto {

    private String title;
    private String contents;

    public Board toEntity(){
        return Board.builder()
                .contents(contents)
                .title(title)
                .build();
    }

    public static BoardDto from(Map<String, String> map){   //factory method only for reflection
        return new BoardDto(map.get("contents"), map.get("title"));
    }
}
