package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DbCallback {
    PreparedStatement makePreparedStatement(Connection con) throws SQLException;
}
