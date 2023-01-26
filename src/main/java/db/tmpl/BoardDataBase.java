package db.tmpl;

import model.Board;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BoardDataBase {

    void addBoard(Board board);

    Optional<Board> findBoardById(Integer boardId);

    void deleteBoard(Integer boardId);


    List<Board> findAll();
}
