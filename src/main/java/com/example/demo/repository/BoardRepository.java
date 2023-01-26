package com.example.demo.repository;

import com.example.demo.model.Board;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class BoardRepository {

    private static final String url = "jdbc:mysql://localhost:3306/borad";
    private static final String id = "root";
    private static final String password = "1234";
    private Connection con = null;
    private PreparedStatement pstt = null;
    private java.sql.Statement st = null;
    private ResultSet result = null;

    public BoardRepository() {
        try {
            con = DriverManager.getConnection(url, id, password);
            st = con.createStatement();
//            result = st.executeQuery("SHOW DATABASES"); // executeQuery : 쿼리를 실행하고 결과를 ResultSet 객체로 반환한다.
//
//            // 결과를 하나씩 출력한다.
//            while (result.next()) {
//                String str = result.getNString(1);
//                System.out.println(str);
//            }
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }

    public void insert(Board board) {
        String sql = "INSERT INTO board VALUES (?,?,?)";

        try{
            pstt = con.prepareStatement(sql);
            pstt.setString(1,board.getWriter());
            pstt.setString(2,board.getTitle());
            pstt.setString(3,board.getContent());
            pstt.executeUpdate();
            log.debug("새로운 레코드를 추가하였습니다 : {}",board.getWriter());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
