package db;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public void insert(User user) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Database database = Database.getInstance();
            Class.forName(database.getD_NAME());
            con = DriverManager.getConnection(database.getURL(), database.getUSER(), database.getPASSWORD());

            String sql = "INSERT INTO USERS(userId, password, name, email) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            logger.debug(">> sql : {}", sql);

            pstmt.executeUpdate();
            logger.debug(">> User {} Saved", user.getUserId());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
