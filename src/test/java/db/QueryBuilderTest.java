package db;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

        // close
        resultSet.close();
        statement.close();
    }

    @Test
    @DisplayName("데이터 조회(where O) 테스트")
    void caseReadWithWhere() throws SQLException {
        // given
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO user VALUES(1, 'sol');");
        statement.execute("INSERT INTO user VALUES(2, 'chan');");

        // when
        ResultSet resultSet = queryBuilder
                .select("user_id", "nickname")
                .from("user")
                .where("user_id", "<", "2")
                .execute();

        // then
        List<Map<String, String>> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(
                    Map.of(
                            "user_id", resultSet.getString("user_id"),
                            "nickname", resultSet.getString("nickname"))
            );
        }

        assertAll(
                () -> assertThat(results.size()).isEqualTo(1),
                () -> assertThat(results.get(0).get("user_id")).isEqualTo("1"),
                () -> assertThat(results.get(0).get("nickname")).isEqualTo("sol")
        );

        // close
        resultSet.close();
        statement.close();
    }

    @Test
    @DisplayName("데이터 조회(다중 where) 테스트")
    void caseReadWithMultiWhere() throws SQLException {
        // given
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO user VALUES(1, 'sol');");
        statement.execute("INSERT INTO user VALUES(2, 'chan');");
        statement.execute("INSERT INTO user VALUES(3, 'kim');");

        // when
        ResultSet resultSet = queryBuilder
                .select("user_id", "nickname")
                .from("user")
                .where("user_id", "<", "3")
                .where("nickname", "!=", "chan")
                .execute();

        // then
        List<Map<String, String>> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(
                    Map.of(
                            "user_id", resultSet.getString("user_id"),
                            "nickname", resultSet.getString("nickname"))
            );
        }

        assertAll(
                () -> assertThat(results.size()).isEqualTo(1),
                () -> assertThat(results.get(0).get("user_id")).isEqualTo("1"),
                () -> assertThat(results.get(0).get("nickname")).isEqualTo("sol")
        );

        // close
        resultSet.close();
        statement.close();
    }
}
