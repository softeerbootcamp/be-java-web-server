package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/was?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
	private String user = "root";
	private String password = "roopre";

	private static DBManager instance; //싱글톤 패턴을 위한 객체 생성


	public static DBManager getInstance() { //객체를 가져오기 위한 함수
		if (instance == null) {
			synchronized (DBManager.class) {
				instance = new DBManager();
			}
		}
		return instance;
	}

	//1. JDBC Driver 로딩
	private DBManager() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//2. DB서버 연결
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	//3. 자원 해제
	public void close(Connection conn, Statement stmt, ResultSet rs) {
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
	public void close(Connection conn, Statement stmt) {
		close(conn, stmt, null);
	}

}