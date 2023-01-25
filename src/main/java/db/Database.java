package db;

import com.google.common.collect.Maps;
import exception.DuplicateUserIdException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Database {
    private static Database instance = new Database();
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private final String D_NAME;
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    private static Map<String, User> users = Maps.newHashMap();

    private Database() {
        File xmlFile = new File("/Users/rentalhub-mac34/Documents/min/be-java-web-server/src/main/resources/database.xml");
        Element element = null;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            NodeList nodeList = doc.getElementsByTagName("database");
            Node node = nodeList.item(0);
            element = (Element) node;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        assert element != null;
        D_NAME = element.getElementsByTagName("driver").item(0).getTextContent();
        URL = element.getElementsByTagName("url").item(0).getTextContent();
        USER = element.getElementsByTagName("user").item(0).getTextContent();
        PASSWORD = element.getElementsByTagName("password").item(0).getTextContent();
    }

    public static Database getInstance() {
        return instance;
    }

    public static void addUser(User user) {

        if (users.containsKey(user.getUserId())) {
            throw new DuplicateUserIdException();
        }
        users.put(user.getUserId(), user);
        logger.debug(">> User {} Saved", user.getUserId());
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void cleanAll() {
        users.clear();
    }



    public String getD_NAME() {
        return D_NAME;
    }

    public String getURL() {
        return URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
}
