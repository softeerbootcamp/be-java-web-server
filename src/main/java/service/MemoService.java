package service;

import db.Database;

public class MemoService {
    private final Database database;

    public MemoService(Database database) {
        this.database = database;
    }
}
