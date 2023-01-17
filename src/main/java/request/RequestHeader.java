package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String BLANK = " ";
    private static final String lineSeparator = System.lineSeparator();

    private final Map<String, String> fields;

    public RequestHeader(Map<String, String> fields) {
        this.fields = fields;
    }

    public static RequestHeader of(BufferedReader bufferedReader) throws IOException {
        Map<String, String> fields = new HashMap<>();
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            if (line.equals(BLANK)) break;
            logger.debug("line : {}",line);

            String[] headerTokens = line.split(": ");
            if (headerTokens.length >= 2) {
                logger.debug("headerToken : {}",headerTokens[0].toLowerCase());
                logger.debug("headerToken : {}",headerTokens[1]);
                fields.put(headerTokens[0].toLowerCase(), headerTokens[1]);
            }
        }
        return new RequestHeader(fields);
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

    public String getContentLength() {
        return fields.get("content-length");
    }
}
