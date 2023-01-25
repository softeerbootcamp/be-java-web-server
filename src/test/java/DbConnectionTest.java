import db.DatabaseConnection;
import org.junit.Test;

import java.sql.Connection;

public class DbConnectionTest {
    @Test
    public void DB_연결_테스트() {
        try {
            Connection con = DatabaseConnection.getConnection();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
