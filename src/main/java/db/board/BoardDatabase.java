package db.board;

import model.Memo;

import java.util.Collection;

public interface BoardDatabase {
    void addMemo(Memo memo);

    Collection<Memo> findAll();
}
