package db;

import com.google.common.collect.Maps;
import model.Board;

import java.util.Collection;
import java.util.Map;

public class BoardDatabase {
    public static Map<String, Board> boards = Maps.newHashMap();

    public static void addBoard(Board board) {
        boards.put(board.getTitle(), board);
    }

    public static Board findBoardByTitle(String title) {
        return boards.get(title);
    }

    public static Collection<Board> findAll() {
        return boards.values();
    }
}
