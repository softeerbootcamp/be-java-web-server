package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class RequestBody {

    private static final Logger logger = LoggerFactory.getLogger(RequestBody.class);

    private final static RequestBody EMPTY_REQUEST_BODY = new RequestBody("");

    private final String body;

    private RequestBody(String body) {
        this.body = body;
    }

    public static RequestBody of(BufferedReader bufferedReader, Optional<String> contentLength) throws IOException {

        if (!contentLength.isPresent()) {
            return EMPTY_REQUEST_BODY;
        }

        logger.debug("BodyContent-Length:{}",contentLength.get());
        String body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength.get()));

        logger.debug("requestOfBody : {}",body);
        return new RequestBody(body);
    }

    public String getBody() {
        return body;
    }
}
