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
    public User findUserByUserId(String userId) {
        return queryForObject("select * from user where userId = ?",
                new Object[]{userId},
                new ResultSetExtractor<User>() {
                    @Override
                    public List<User> extractData(ResultSet rs) throws SQLException {
                        List<User> list = new ArrayList<>();
                        while (rs.next()) {
                            list.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email")));
                        }
                        return list;
                    }
                }).get(0);
    }

    private <T> List<T> queryForObject(String sql, Object[] objects, ResultSetExtractor<T> resultSetExtractor) {
        return query(new DbCallback() {
            @Override
            public PreparedStatement makePreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);

                if (objects != null && objects.length > 0) {
                    for (int i = 1; i < objects.length + 1; i++) {
                        ps.setString(i, (String) objects[i - 1]);
                    }
                }
                return ps;
            }
        }, resultSetExtractor);
    }

    public <T> List<T> query(DbCallback callback, ResultSetExtractor<T> resultSetExtractor) {
        String jdbc_url = "jdbc:mysql://localhost:3306/webserver?serverTimezone=UTC";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(jdbc_url, "root", "84338253");

            ps = callback.makePreparedStatement(con);
            rs = ps.executeQuery();
            return resultSetExtractor.extractData(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {

                }
            }
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
