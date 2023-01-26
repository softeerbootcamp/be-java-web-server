package db;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private static String D_NAME;
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            File xmlFile = new File("/Users/rentalhub-mac34/Documents/min/be-java-web-server/src/main/resources/database.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            NodeList nodeList = doc.getElementsByTagName("database");
            Node node = nodeList.item(0);
            Element element = (Element) node;

            assert element != null;
            D_NAME = element.getElementsByTagName("driver").item(0).getTextContent();
            URL = element.getElementsByTagName("url").item(0).getTextContent();
            USER = element.getElementsByTagName("user").item(0).getTextContent();
            PASSWORD = element.getElementsByTagName("password").item(0).getTextContent();

            Class.forName(D_NAME);

        } catch (ClassNotFoundException | ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void close(AutoCloseable... autoCloseables) {
        try {
            for (AutoCloseable ac : autoCloseables) {
                if (ac != null)
                    ac.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
