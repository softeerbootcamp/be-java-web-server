package db.jdbc;

import db.tmpl.ConnectionManager;
import db.tmpl.UserDatabase;
import model.Board;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUserDatabase implements UserDatabase {

    private ConnectionManager connectionManager;
    private JdbcUserDatabase(){}

    public static JdbcUserDatabase getInstance(){
        return JdbcUserDatabase.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final JdbcUserDatabase INSTANCE = new JdbcUserDatabase();
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void addUser(User user) {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("insert into user(userId, password, name, email) values (?,?,?,?)")){
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findUserById(String userId) {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("select * from user where userId = ?")) {
            ps.setString(1, userId);

            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst())
                return Optional.empty();
            rs.next();
            User user = new User(rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"));
            return Optional.of(user);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("select * from user")){
            ResultSet rs = ps.executeQuery();
            List<User> userList = new ArrayList<>();
            while(rs.next()) {
                User user = new User(rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
                userList.add(user);
            }
            return userList;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
