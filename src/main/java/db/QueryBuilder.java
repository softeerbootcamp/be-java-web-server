package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class QueryBuilder implements AutoCloseable {
    private final Connection conn;
    private Statement statement;
    private ResultSet resultSet;

    private String front = "";
    private String from = "";
    private String where = "";
    private String values = "";
    private String order = "";

    public QueryBuilder(Connection conn) {
        this.conn = conn;
    }

    public QueryBuilder insert(String... values) {
        StringBuilder sb = new StringBuilder("VALUES (");

        for (int idx = 0; idx < values.length - 1; idx++) {
            if (values[idx] == null) {
                sb.append(values[idx]).append(", ");
                continue;
            }
            sb.append("\"").append(values[idx]).append("\"").append(", ");
        }
        sb.append("\"").append(values[values.length - 1]).append("\"").append(")");
        this.values = sb.toString();
        return this;
    }

    public QueryBuilder into(String table) {
        front = "INSERT INTO " + table + " " + values;
        from = "";
        where = "";
        return this;
    }

    public QueryBuilder select(String... filed) {
        StringBuilder sb = new StringBuilder("SELECT ");

        for (int idx = 0; idx < filed.length - 1; idx++) {
            sb.append(filed[idx]).append(", ");
        }
        sb.append(filed[filed.length - 1]).append(" ");

        this.front = sb.toString();
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

    public QueryBuilder order(String filed, String order) {
        this.order = " ORDER BY " + filed + " " + order;
        return this;
    }

    public ResultSet read() {
        String sql = front + from + where + order + ";";
        try {
            System.out.println(sql);
            statement = conn.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            reset();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean fetch() {
        try {
            String sql = front + from + where + ";";
            System.out.println(sql);
            statement = conn.createStatement();
            statement.execute(sql);
            reset();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void reset() {
        front = "";
        from = "";
        where = "";
        values = "";
        order = "";
    }

    @Override
    public void close() {
        try {
            if (Objects.nonNull(resultSet)) {
                resultSet.close();
            }
            if (Objects.nonNull(statement)) {
                statement.close();
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
