package db;

import com.google.common.collect.Maps;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.DatabaseConnHandler;
import webserver.RequestResponseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class UserDatabase {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);
    private static Map<String, User> users = Maps.newHashMap();
    private static DatabaseConnHandler databaseConnHandler;
    private static Connection conn;
    public static void addUser(User user) {
        try{
            conn = databaseConnHandler.dbConnection();
            String sql = "insert into User values(?,?,?,?)";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,user.getUserId());
            psmt.setString(2,user.getPassword());
            psmt.setString(3,user.getName());
            psmt.setString(4,user.getEmail());
            psmt.executeUpdate();
            conn.close();
            users.put(user.getUserId(), user);
        }catch (SQLException sqlException){
            logger.debug("sql exception occurred : "+sqlException);
        }

    }
    // todo : findUserById 할때마다 커넥션 형성하는데, 비용이 너무 큰 느낌이다. 사용처를 다시 확인하고,리펙토링하자
    public static User findUserById(String userId) throws SQLException {
        User user = null;
        conn = databaseConnHandler.dbConnection();
        //SELECT * FROM User WHERE id = [id];
        String sql = "SELECT * FROM User WHERE id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1,userId);
        ResultSet rs = psmt.executeQuery();
        if (rs.next()) {
            user = new User(rs.getString("ID"),rs.getString("Password"),
                    rs.getString("Name"),rs.getString("Email") );
        }
        conn.close();
        return user;
    }

    public static Collection<User> findAll() {
        conn = databaseConnHandler.dbConnection();
        String sql = "SELECT * FROM User WHERE id = ?";

        return users.values();
    }


}
