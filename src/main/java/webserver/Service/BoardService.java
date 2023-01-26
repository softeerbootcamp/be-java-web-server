package webserver.Service;

import db.memoryDB.MemoryBoardDatabase;
import db.memoryDB.MemoryCommentDatabase;
import db.tmpl.BoardDataBase;
import db.tmpl.CommentDatabase;
import model.Board;
import model.Comment;
import model.dto.BoardDto;
import model.dto.CommentDto;
import model.dto.ReadBoardDto;
import webserver.security.SecurityContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BoardService {

    private BoardDataBase boardDataBase;
    private CommentDatabase commentDatabase;
    private BoardService (){}

    public static BoardService getInstance(){
        return BoardService.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{   //Singleton
        private static final BoardService INSTANCE = new BoardService();
    }

    public void setBoardDataBase(BoardDataBase boardDataBase){
        this.boardDataBase = boardDataBase;
    }

    public void setCommentDatabase(CommentDatabase commentDatabase){
        this.commentDatabase = commentDatabase;
    }

    public void addBoard(BoardDto dto) {
        Board board = dto.toEntity();
        board.addMetadata(SecurityContext.getContext().getUserId(), SecurityContext.getContext().getName(), LocalDateTime.now());
        boardDataBase.addBoard(board);
    }

    public void addComment(String contents, int commentId){
        Comment comment = Comment.builder().contents(contents).commentId(commentId).build();
        comment.addMetadata(SecurityContext.getContext().getUserId(), SecurityContext.getContext().getName(), LocalDateTime.now());
        commentDatabase.addComment(comment);
    }
    public ReadBoardDto readBoard(int boardId) {
        Board board = boardDataBase.findBoardById(boardId).orElse(null);
        BoardDto boardDto = board.toDto();
        List<CommentDto> commentDtoList = commentDatabase.findAll().stream()
                .filter(comment -> comment.getBoardId() == boardId)
                .map(Comment::toDto)
                .collect(Collectors.toList());
        return new ReadBoardDto(boardDto, commentDtoList);
    }


    public List<Board> getBoardList() {   //TODO : 이 메소드의 존재 이유를 고민해보자
        return boardDataBase.findAll();
    }

}
