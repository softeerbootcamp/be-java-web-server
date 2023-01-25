package model.repository;

import model.domain.Memo;
import model.domain.User;

import java.util.Collection;
import java.util.Optional;

public interface MemoRepository {
    Memo addMemo(Memo memo);

    Collection<Memo> findRecent(int count);
    Collection<Memo> findAll();
}
