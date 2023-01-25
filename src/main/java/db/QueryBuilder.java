package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryBuilder {
    private final Connection conn;

    public QueryBuilder(Connection conn) {
        this.conn = conn;
    }

    public QueryBuilder insert(String... field) {
        return this;
    }

    public QueryBuilder select(String... filed) {
      return this;
    }

    public QueryBuilder from(String table) {
        return this;
    }

    public QueryBuilder where(String key, String condition, String value) {
        return this;
    }

    public ResultSet execute() {
        return null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
