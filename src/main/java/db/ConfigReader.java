package db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class ConfigReader {
    public static Map<String, String> read(String path) {
        File file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            NodeList nodes = document.getElementsByTagName("db");

            Element item = (Element) nodes.item(0);
            return Map.of(
                    "host", item.getElementsByTagName("host").item(0).getTextContent(),
                    "port", item.getElementsByTagName("port").item(0).getTextContent(),
                    "database", item.getElementsByTagName("database").item(0).getTextContent(),
                    "user", item.getElementsByTagName("user").item(0).getTextContent(),
                    "password", item.getElementsByTagName("password").item(0).getTextContent()
            );
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // TODO: 좀 더 세부적인 예외 처리 진행하기
            throw new RuntimeException(e);
        }
    }
}
