package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private final int poolSize;
    private final String url;
    private final String user;
    private final String password;
    private BlockingDeque<Connection> connections;

    public ConnectionPool(int poolSize, String url, String user, String password) {
        this.poolSize = poolSize;
        this.url = url;
        this.user = user;
        this.password = password;
        connections = new LinkedBlockingDeque<>();
    }

    public void run() {
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                connections.put(connection);
            } catch (SQLException | InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
        logger.debug("run :" + connections.size());
    }

    public Connection getConnection() {
        try {
            logger.debug(String.valueOf(connections.size()));
            return connections.take();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public void releaseConnection(Connection connection) {
        try {
            connections.put(connection);
            logger.debug(String.valueOf(connections.size()));
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
}
