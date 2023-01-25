package db.board;

import model.Memo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class MySqlBoardDatabase implements BoardDatabase {
    private static final Logger logger = LoggerFactory.getLogger(MySqlBoardDatabase.class);

    @Override
    public Collection<Memo> findAll() {
        return null;
    }

    @Override
    public void addMemo(Memo memo) {

    }
}
