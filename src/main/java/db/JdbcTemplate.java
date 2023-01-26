package db;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void insertIntoUserDb(User user) {
        executeUpdate("insert into user(userId, password, name, email) values (?, ? ,?, ?)",
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void executeUpdate(final String sql, final String... param) {
        updateWithCallback((con) -> {
                    PreparedStatement ps = con.prepareStatement(sql);

                    if (param != null) {
                        for (int i = 1; i <= param.length; i++) {
                            ps.setString(i, param[i - 1]);
                        }
                    }
                    return ps;
                }
        );
    }

    private void updateWithCallback(DbCallback callback) {
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
