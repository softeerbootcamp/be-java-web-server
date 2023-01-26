package db;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTemplate {
    public void insertIntoUserDb(User user) {
        workWithStatementStrategy(new DbCallback() {
            @Override
            public PreparedStatement makePreparedStatement(Connection con) throws SQLException{
                PreparedStatement ps = con.prepareStatement("insert into user(userId, password, name, email) values (?, ? ,?, ?)");

                ps.setString(1, user.getUserId());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getName());
                ps.setString(4, user.getEmail());
                return ps;
            }
        });
    }
    public void workWithStatementStrategy(DbCallback callback) {
        String jdbc_url = "jdbc:mysql://localhost:3306/webserver?serverTimezone=UTC";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(jdbc_url, "root", "84338253");

            ps = callback.makePreparedStatement(con);
            ps.executeUpdate();
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
