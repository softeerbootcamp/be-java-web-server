package model.repository;
import model.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MysqlUserRepository implements UserRepository{

    private static MysqlUserRepository mysqlUserRepository;
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private static final String url = "jdbc:mysql://localhost:3306/jdbc";
    private static final String userName = "root";
    private static final String password = "vldb1234";

    public static UserRepository getInstance(){
        if (mysqlUserRepository == null){
            synchronized (MysqlUserRepository.class){
                if (mysqlUserRepository == null){
                    mysqlUserRepository = new MysqlUserRepository();
                }
            }
        }

        return mysqlUserRepository;
    }

    @Override
    public User addUser(User user) {
        String sql = "insert into users(userId, password, name, email) values(?, ?, ?, ?)";
        try {
            conn = DriverManager.getConnection(url,userName,password);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2,user.getPassword());
            pstmt.setString(3,user.getName());
            pstmt.setString(4,user.getEmail());
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Optional<User> findUserById(String userId) {
        String sql = "select * from users where userId = ?";
        try {
            conn = DriverManager.getConnection(url,userName,password);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(rs.getString("userId"),rs.getString("password"),
                    rs.getString("name"),rs.getString("email"));
            rs.close();
            pstmt.close();
            conn.close();
            return Optional.of(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Collection<User> findAll() {
        String sql = "select * from users";
        Collection<User> users = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url,userName,password);
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                User user = new User(rs.getString("userId"),rs.getString("password"),
                        rs.getString("name"),rs.getString("email"));
                users.add(user);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }
}
