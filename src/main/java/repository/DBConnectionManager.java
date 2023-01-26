package repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(DBConnectionManager.class);
    private static final String SECRET_PATH = DBConnectionManager.class.getClassLoader().getResource("./secrets.txt").getPath();

    private static String url;
    private static String id;
    private static String pw;

    static {
        initConnection();
    }

    private static void initConnection() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(SECRET_PATH));
            url = br.readLine();
            id = br.readLine();
            pw = br.readLine();

            Class.forName("com.mysql.cj.jdbc.Driver");
            br.close();
        } catch (ClassNotFoundException e) {
            logger.error("JDBC 드라이버 로딩 실패");
        } catch (IOException e) {
            logger.error("비밀번호 파일을 찾지 못함.");
        }
    }

    public static List<Map<String, String>> sendSql(String sql, String[] arguments) {
        try(Connection conn = DriverManager.getConnection(url, id, pw);
            PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            for (int i = 0; arguments != null && i < arguments.length; i++) {
                statement.setString(i + 1, arguments[i]);
            }
            statement.execute();
            ResultSet result = statement.getResultSet();
            List<Map<String, String>> results = new ArrayList<>();
            while (result != null && result.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    row.put(result.getMetaData().getColumnName(i), result.getString(i));
                }
                results.add(row);
            }
            return results;
        } catch (SQLException e) {
            logger.error("잘못된 SQL 입니다.");
            throw new RuntimeException(e);
        }
    }
}
