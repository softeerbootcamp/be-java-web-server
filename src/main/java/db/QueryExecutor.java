package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryExecutor {
    public static List<Map<String, String>> executeSelectQuery(String query, String[] args) throws SQLException, NullPointerException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            if (args != null) {
                int index = 1;
                for (String arg : args) {
                    pstmt.setString(index, arg);
                    index++;
                }
                if (index - 1 != args.length) {
                    throw new SQLException();
                }
            }

            ResultSet rs = pstmt.executeQuery();
            List<Map<String, String>> resultList = new ArrayList<>();
            int columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                }
                resultList.add(row);
            }
            return resultList;
        }
    }

    public static void executeUpdateQuery(String query, String[] args) throws SQLException, NullPointerException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
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
        }
    }
}
