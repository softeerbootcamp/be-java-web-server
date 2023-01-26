package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReadBoardDto {

    private BoardDto boardDto;
    private List<CommentDto> commentDtos = new ArrayList<>();

}
