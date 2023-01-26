package db.session;

import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlSessionDatabase implements SessionDatabase {
    private static final Logger logger = LoggerFactory.getLogger(MySqlSessionDatabase.class);
    private final ConnectionPool connectionPool;

    public MySqlSessionDatabase(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void addSession(Session session) {
        try {
            Connection connection = connectionPool.getConnection();
            String insertSQL = "INSERT INTO sessions VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, session.getSessionId());
                preparedStatement.setString(2, session.getUserId());
                preparedStatement.setLong(3, session.getTimeStamp());
                int count = preparedStatement.executeUpdate();
                if (count == 1)
                    logger.debug("데이터 입력 성공");
            } finally {
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Session findSessionById(String sessionId) {
        try {
            Connection connection = connectionPool.getConnection();
            String selectSQL = "SELECT * FROM sessions WHERE session_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                preparedStatement.setString(1, sessionId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String user_id = resultSet.getString("user_id");
                        long timestamp = resultSet.getLong("timestamp");
                        return Session.createWithTimeStamp(sessionId, user_id, timestamp);
                    }
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Session> findAll() {
        try {
            Connection connection = connectionPool.getConnection();
            Collection<Session> sessions = new ArrayList<>();
            String selectSQL = "SELECT * FROM sessions";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String sessionId = resultSet.getString("session_id");
                    String userId = resultSet.getString("user_id");
                    long timestamp = resultSet.getLong("timestamp");
                    sessions.add(Session.createWithTimeStamp(sessionId, userId, timestamp));
                }
                return sessions;
            } finally {
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteSession(String sessionId) {
        try {
            Connection connection = connectionPool.getConnection();
            String selectSQL = "DELETE FROM sessions WHERE session_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                preparedStatement.setString(1, sessionId);
                int count = preparedStatement.executeUpdate();
                if (count == 1)
                    logger.debug("데이터 삭제 성공");
            } finally {
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
