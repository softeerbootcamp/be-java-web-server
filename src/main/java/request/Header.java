package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.Map;

public class Header {
    private static final String lineSeparator = System.getProperty("line.separator");
    private static final Logger logger = LoggerFactory.getLogger(Header.class);

    private Map<String, String> fields;

    public Header(Map<String, String> fields) {
        this.fields = fields;
    }

    public String toValue() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> field : fields.entrySet()) {
            logger.debug("getKey : {}" , field.getKey());
            logger.debug("getValue : {}" , field.getValue());
            sb.append(String.format("%s: %s%s", field.getKey(), field.getValue(), lineSeparator));
        }

        return sb.toString();
    }
}

