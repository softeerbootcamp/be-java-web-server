package db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public final class QueryBuilderFactory {
    public static QueryBuilder newQueryBuilder(String configPath) {
        Map<String, String> config = ConfigReader.read(configPath);
        String url = "jdbc:mysql://" +
                config.get("host") + ":" +
                config.get("port") + "/" +
                config.get("database") + "?" +
                "user=" + config.get("user") +
                "&password=" + config.get("password");
        try {
            return new QueryBuilder(DriverManager.getConnection(url));
        } catch (SQLException e) {
            // TODO: 예외처리 좀 더 세부적으로 진행하기
            throw new RuntimeException(e);
        }
    }
}
