package webserver.Service;

import db.BoardDatabase;
import db.CommentDatabase;
import model.Board;
import model.Comment;
import model.dto.BoardDto;
import model.dto.CommentDto;
import model.dto.ReadBoardDto;
import webserver.security.SecurityContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BoardService {

    private BoardService (){}

    public static BoardService getInstance(){
        return BoardService.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{   //Singleton
        private static final BoardService INSTANCE = new BoardService();
    }

    public void addBoard(BoardDto dto) {
        Board board = dto.toEntity();
        board.addMetadata(BoardDatabase.boardId.get(), SecurityContext.getContext().getUserId(), SecurityContext.getContext().getName(), LocalDateTime.now());
        BoardDatabase.addBoard(board);
    }

    public void addComment(String contents, int commentId){
        Comment comment = Comment.builder().contents(contents).commentId(commentId).build();
        comment.addMetadata(BoardDatabase.boardId.get(), SecurityContext.getContext().getUserId(), SecurityContext.getContext().getName(), LocalDateTime.now());
        CommentDatabase.addComment(comment);
    }
    public ReadBoardDto readBoard(int boardId) {
        Board board = BoardDatabase.findBoardById(boardId).orElse(null);
        BoardDto boardDto = board.toDto();
        List<CommentDto> commentDtoList = CommentDatabase.findAll().stream()
                .filter(comment -> comment.getBoardId() == boardId)
                .map(Comment::toDto)
                .collect(Collectors.toList());
        return new ReadBoardDto(boardDto, commentDtoList);
    }


    public List<Board> getBoardList() {   //TODO : 이 메소드의 존재 이유를 고민해보자
        return BoardDatabase.findAll();
    }

}
