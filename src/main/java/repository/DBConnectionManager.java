package repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(DBConnectionManager.class);
    private static final String SECRET_PATH = DBConnectionManager.class.getClassLoader().getResource("./secrets.txt").getPath();
    private static Connection conn;

    static{
        initConnection();
    }

    private static void initConnection() {
        try{
            BufferedReader br = new BufferedReader(new FileReader(SECRET_PATH));
            String url = br.readLine();
            String id = br.readLine();
            String pw = br.readLine();

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            br.close();
        }
        catch(ClassNotFoundException e){
            logger.error("JDBC 드라이버 로딩 실패");
        }
        catch(SQLException e){
            logger.error("에러: " + e);
        }
        catch (IOException e) {
            logger.error("비밀번호 파일을 찾지 못함.");
        }
    }

    public static List<List<String>> sendSql(String sql, String[] arguments) {
        try {
            if(conn == null || conn.isClosed()) {
                logger.info("trying re connection");
                initConnection();
            }
            PreparedStatement statement = conn.prepareStatement(sql, arguments);
            for(int i=0; arguments != null && i< arguments.length; i++){
                statement.setString(i+1, arguments[i]);
            }
            statement.execute();
            ResultSet result = statement.getResultSet();
            List<List<String>> results = new ArrayList<>();
            while (result != null && result.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    row.add(result.getString(i));
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
