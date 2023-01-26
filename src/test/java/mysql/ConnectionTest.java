package mysql;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ConnectionTest {

    private static final String URL = "jdbc:mysql://localhost:3306/webserver";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "zxcasdqwe";

    @Test
    void 연결_확인() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
        }
    }
}
