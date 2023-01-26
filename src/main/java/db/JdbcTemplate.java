package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTemplate {
    public  void workWithStatementStrategy() {
        String jdbc_url = "jdbc:mysql://localhost:3306/webserver?serverTimezone=UTC";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.Jdbc.Driver");
            con = DriverManager.getConnection(jdbc_url, "root", "84338253");

            ps = con.prepareStatement("somthing");
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    //TODO 나중에 구현
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    //TODO 나중에 구현
                }
            }
        }
    }
}
