package service;

import db.Database;
import model.domain.User;

import java.util.Map;

public class MemoService {
    private final Database database;

    public MemoService(Database database) {
        this.database = database;
    }

    public void writeMemo(User user, Map<String, String> memoInfo) {
        database.addMemo(user.getUserId(), memoInfo.get("contents"));
    }
}
