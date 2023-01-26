package db.memoryDB;

import com.google.common.collect.Maps;
import db.tmpl.BoardDataBase;
import model.Board;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryBoardDatabase implements BoardDataBase {

    public static final AtomicInteger boardId = new AtomicInteger();
    private MemoryBoardDatabase(){}

    public static MemoryBoardDatabase getInstance(){
        return MemoryBoardDatabase.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{   //Singleton
        private static final MemoryBoardDatabase INSTANCE = new MemoryBoardDatabase();
    }
    private static Map<Integer, Board> boards = Maps.newHashMap();

    @Override
    public void addBoard(Board board) {
        boards.put(boardId.incrementAndGet(), board);
    }

    @Override
    public Optional<Board> findBoardById(Integer boardId) {
        return Optional.ofNullable(boards.get(boardId));
    }

    @Override
    public void deleteBoard(Integer boardId){
        boards.remove(boardId);
    }

    @Override
    public List<Board> findAll() {
        return new ArrayList<>(boards.values());
    }

}
