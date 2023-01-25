package db.memo;

import model.Memo;

import java.util.Collection;

public interface MemoDatabase {
    void addMemo(Memo memo);

    Collection<Memo> findAll();
}
