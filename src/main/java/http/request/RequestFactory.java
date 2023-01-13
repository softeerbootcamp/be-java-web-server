package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RequestFactory {
    private final Logger logger = LoggerFactory.getLogger(RequestFactory.class);

    public HttpRequest create(InputStream in) throws IOException {
        BufferedReader bufferedReader = getBufferedReader(in);

        RequestStartLine startLine = new RequestStartLine(bufferedReader.readLine());
        Header header = new Header(getRemainMessages(bufferedReader));
        RequestParameter requestParameter = new RequestParameter(startLine);

        return new HttpRequest(startLine, header, requestParameter);
    }

    private String getRemainMessages(BufferedReader bufferedReader) {
        String msg = "";
        String line;
        try {
            while (!(line = bufferedReader.readLine()).isEmpty()) {
                msg += line + "\n\r";
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return msg;
    }

    private BufferedReader getBufferedReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}
