package db.memo;

import model.Memo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class MemoryMemoDatabase implements MemoDatabase {
    private static final Logger logger = LoggerFactory.getLogger(MemoryMemoDatabase.class);

    @Override
    public Collection<Memo> findAll() {
        return null;
    }

    @Override
    public void addMemo(Memo memo) {

    }
}
