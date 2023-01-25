package db.session;

import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class MySqlSessionDatabase implements SessionDatabase {
    private static final Logger logger = LoggerFactory.getLogger(MySqlSessionDatabase.class);
    private final ConnectionPool connectionPool;

    public MySqlSessionDatabase(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void addSession(Session session) {
        try (Connection connection = connectionPool.getConnection()) {
            String insertSQL = "INSERT INTO sessions VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, session.getSessionId());
            preparedStatement.setString(2, session.getUserId());
            preparedStatement.setLong(3, session.getTimeStamp());
            int count = preparedStatement.executeUpdate();
            if (count == 1)
                logger.debug("데이터 입력 성공");
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Session findSessionById(String sessionId) {
        return null;
    }

    @Override
    public Collection<Session> findAll() {
        return null;
    }

    @Override
    public void deleteSession(String sessionId) {

    }
}
