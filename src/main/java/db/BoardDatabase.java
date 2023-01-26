package db;

import com.google.common.collect.Maps;
import model.Board;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class BoardDatabase {

    public static final AtomicInteger boardId = new AtomicInteger();
    private BoardDatabase (){}

    public static BoardDatabase getInstance(){
        return BoardDatabase.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{   //Singleton
        private static final BoardDatabase INSTANCE = new BoardDatabase();
    }

    private static Map<Integer, Board> boards = Maps.newHashMap();

    public static void addBoard(Board board) {
        boards.put(boardId.incrementAndGet(), board);
    }

    public static Optional<Board> findBoardById(Integer boardId) {
        return Optional.ofNullable(boards.get(boardId));
    }

    public static void deleteBoard(Integer boardId){
        boards.remove(boardId);
    }

    public static List<Board> findAll() {
        return new ArrayList<>(boards.values());
    }
}
