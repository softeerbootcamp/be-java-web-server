package com.example.demo.repository;

import com.example.demo.model.Board;
import org.springframework.stereotype.Repository;

import java.sql.*;

public class boardRepository {

    private static final String url = "jdbc:mysql://localhost:3306/borad";
    private static final String id = "root";
    private static final String password = "1234";

    public void insert(Board board) {
        try {
            Connection con = null;
            PreparedStatement preparedStatement = null;

            con = DriverManager.getConnection(url, id, password);

            java.sql.Statement st = null;
            ResultSet result = null;
            st = con.createStatement();
            result = st.executeQuery("SHOW DATABASES"); // executeQuery : 쿼리를 실행하고 결과를 ResultSet 객체로 반환한다.

            // 결과를 하나씩 출력한다.
            while (result.next()) {
                String str = result.getNString(1);
                System.out.println(str);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }
}
