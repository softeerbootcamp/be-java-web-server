package db;

import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SessionDatabase {
    private static final Logger logger = LoggerFactory.getLogger(SessionDatabase.class);

    private static final SessionDatabase instance;

    private SessionDatabase() {}

    static {
        instance = new SessionDatabase();
    }

    public static SessionDatabase getInstance() {
        return instance;
    }

    public void add(Session session) throws SQLException {
        String query = "INSERT INTO Sessions (id, expirationTime, userId) VALUES(?, ?, ?)";
        String[] args = new String[3];
        args[0] = session.getId();
        args[1] = session.getExpirationTime().toString();
        args[2] = session.getUserId();

        QueryExecutor.executeUpdateQuery(query, args);
    }

    public Optional<Session> findById(String sid) throws SQLException {
        String query = "SELECT * FROM Sessions WHERE id = ?";
        String[] args = new String[1];
        args[0] = sid;

        List<Map<String, String>> result = QueryExecutor.executeSelectQuery(query, args);

        if(result.size() == 0) {
            return Optional.empty();
        }

        String id = result.get(0).get("id");
        String userId = result.get(0).get("userId");

        return Optional.of(Session.of(id, userId));
    }

    public boolean existsBySessionId(String sid) throws SQLException {
        String query = "SELECT * FROM Sessions WHERE id = ?";
        String[] args = new String[1];
        args[0] = sid;

        List<Map<String, String>> result = QueryExecutor.executeSelectQuery(query, args);

        return !result.isEmpty();
    }

    public void deleteSession(String sid) throws SQLException {
        String query = "DELETE FROM Sessions WHERE id = ?";
        String[] args = new String[1];
        args[0] = sid;

        QueryExecutor.executeUpdateQuery(query, args);
    }
}
