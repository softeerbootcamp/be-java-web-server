package util.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import webserver.RequestHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static ConfigReader configReader;

    private static final String PROPERTIES_PATH = "src/main/resources/application.properties";
    private static Map<String, String> config = new HashMap<>();

    public static String KEY_DB_URL = "database.url";
    public static String KEY_DB_USERNAME = "database.username";
    public static String KEY_DB_PASSWORD = "database.password";

    private ConfigReader()  {
        try (InputStream in = new FileInputStream(PROPERTIES_PATH)){
            Yaml yaml = new Yaml();
            config = yaml.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[ERROR] application.properties 읽어오는데 문제 발생");
        }
    }

    public static ConfigReader getInstance()  {
        if (configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader;
    }

    public  Map<String, String> getConfig() {
        return config;
    }
}
