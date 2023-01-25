package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DBConnector {
    static Logger logger = LoggerFactory.getLogger(DBConnector.class);
    public static Connection connect(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/was", "root","13579310");

        } catch (ClassNotFoundException e){
            logger.debug("jdbc driver 에러");
        } catch (SQLException e){
            logger.debug("...에러");
        }
        return Objects.requireNonNull(conn);
    }
}
