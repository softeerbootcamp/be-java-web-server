package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private final int poolSize;
    private final String url;
    private final String user;
    private final String password;
    private List<Connection> connections;

    public ConnectionPool(int poolSize, String url, String user, String password) {
        this.poolSize = poolSize;
        this.url = url;
        this.user = user;
        this.password = password;
        connections = new ArrayList<>();
    }

    public void run() {
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                connections.add(connection);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        logger.debug("run :" + connections.size());
    }

    public Connection getConnection() {
        logger.debug(String.valueOf(connections.size()));
        while (connections.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error("Error waiting for connection" + e.getMessage());
            }
        }
        return connections.remove(0);
    }

    public void releaseConnection(Connection connection) {
        connections.add(connection);
        logger.debug(String.valueOf(connections.size()));
    }
}
