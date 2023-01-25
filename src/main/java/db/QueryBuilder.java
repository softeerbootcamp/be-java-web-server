package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryBuilder {
    private final Connection conn;
    private String select = "";
    private String from = "";
    private String where = "";

    public QueryBuilder(Connection conn) {
        this.conn = conn;
    }

    public QueryBuilder insert(String... values) {
        return this;
    }

    public QueryBuilder into(String table) {
        return this;
    }

    public QueryBuilder select(String... filed) {
        StringBuilder sb = new StringBuilder("SELECT ");

        for (int idx = 0; idx < filed.length - 1; idx++) {
            sb.append(filed[idx]).append(", ");
        }
        sb.append(filed[filed.length - 1]).append(" ");

        this.select = sb.toString();
        return this;
    }

    public QueryBuilder from(String table) {
        this.from = "FROM " + table + " ";
        return this;
    }

    public QueryBuilder where(String key, String condition, String value) {
        String string = key + condition + "\"" + value + "\"";
        this.where = this.where.isEmpty()
                ? "WHERE " + string
                : this.where + " AND " + string;

        return this;
    }

    public ResultSet execute() {
        String sql = select + from + where + ";";
        try {
            Statement statement = conn.createStatement();
            statement.execute(sql);
            System.out.println(sql);
            return statement.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
