package db;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("QueryBuilder Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueryBuilderTest {
    final Connection connection;
    final QueryBuilder queryBuilder = QueryBuilderFactory.newQueryBuilder("src/test/resources/db_config.xml");

    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?user=root&password=1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    String tableSQL = "CREATE TABLE test.user (\n" +
            " user_id INT NOT NULL,\n" +
            " nickname VARCHAR(20),\n" +
            "  PRIMARY KEY(user_id)\n" +
            ") ENGINE=InnoDB CHARSET=utf8mb4";

    @BeforeAll
    void init() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(tableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    void close() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE test.user;");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("데이터 조회(where X) 테스트")
    void caseRead() throws SQLException {
        // given
        String userId = "1";
        String nickname = "sol";
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO user VALUES(" + userId+ ", '" + nickname + "');");

        // when
        ResultSet resultSet = queryBuilder
                .select("*")
                .from("user")
                .execute();

        // then
        Map<String, String> results = new HashMap<>();
        while (resultSet.next()) {
            results.put("user_id", resultSet.getString("user_id"));
            results.put("nickname", resultSet.getString("nickname"));
        }

        assertAll(
                () -> assertThat(results.get("user_id")).isEqualTo(userId),
                () -> assertThat(results.get("nickname")).isEqualTo(nickname)
        );

        resultSet.close();
    }

    @Test
    @DisplayName("데이터 조회(where O) 테스트")
    void caseReadWithWhere() {

    }

    @Test
    @DisplayName("데이터 조회(다중 where) 테스트")
    void caseReadWithMultiWhere() {

    }
}
