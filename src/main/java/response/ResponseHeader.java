package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private static final String lineSeparator = System.lineSeparator();
    private static final Logger logger = LoggerFactory.getLogger(ResponseHeader.class);

    private Map<String, String> fields;

    public ResponseHeader(Map<String, String> fields) {
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

    public Map<String, String> getFields() {
        return this.getFields();
    }

    public String getCookie() {
        return fields.get("Set-Cookie");
    }
}
