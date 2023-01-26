package db;

import java.sql.*;

public class DBManager {
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://127.0.0.1:3306/qna_db?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
    private String user = "root";
    private String password = "1234";

    private static DBManager instance; //싱글톤 패턴을 위한 객체 생성

    public static DBManager getInstance() { //객체를 가져오기 위한 함수
        if (instance == null) {
            synchronized (DBManager.class) {
                instance = new DBManager();
            }
        }
        return instance;
    }

    //1. JDBC Driver 클래스 로드 시키기
    private DBManager() {
        try {
            // forName의 역할이 단지 Class 객체를 반환하는 것이 아니라 클래스 로더에 의해 그 클래스 객체가 로드된다
            Class.forName(driver); //Driver 클래스 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //2. DB서버 연결
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    //현재 autocloseable을 활용해서 try-catch문으로 응용하므로 close함수는 따로 부르지 않음

    //3. 모든 자원들 해제
    public void close(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //3. 자원 해제 함수 오버로딩
    public void close(Connection conn, PreparedStatement stmt) {
        close(conn, stmt, null);
    }

}
