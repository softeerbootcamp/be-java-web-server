package util.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBConnector {

    private static final Logger logger = LoggerFactory.getLogger(DBConnector.class);
    private ConfigReader configReader;

    private Connection connection;

    public DBConnector() throws SQLException {
        configReader = ConfigReader.getInstance();

       connection = DriverManager.getConnection(
                configReader.getConfig().get(ConfigReader.KEY_DB_URL),
                configReader.getConfig().get(ConfigReader.KEY_DB_USERNAME),
                configReader.getConfig().get(ConfigReader.KEY_DB_PASSWORD));
    }

    public Connection getConnection() {
        return connection;
    }


}
