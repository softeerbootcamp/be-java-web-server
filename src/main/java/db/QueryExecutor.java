package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecutor {
    public static ResultSet executeSelectQuery(String query, String[] args) throws SQLException, NullPointerException {
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(query);

            if (args != null) {
                int index = 1;
                for (String arg : args) {
                    pstmt.setString(index, arg);
                    index++;
                }
                if (index != args.length) {
                    throw new SQLException();
                }
            }

            return pstmt.executeQuery();
        } finally {
            con.close();
        }
    }

    public static void executeUpdateQuery(String query, String[] args) throws SQLException, NullPointerException {
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(query);

            if (args != null) {
                int index = 1;
                for (String arg : args) {
                    pstmt.setString(index, arg);
                    index++;
                }
                if (index-1 != args.length) {
                    throw new SQLException();
                }
            }

            pstmt.executeUpdate();
        } finally {
            con.close();
        }
    }
}
