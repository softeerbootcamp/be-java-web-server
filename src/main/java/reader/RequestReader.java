package reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.fileReader.FileReader;
import util.HttpMethod;
import request.HttpRequest;
import util.error.HttpsErrorMessage;

import java.net.ProtocolException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface RequestReader {
    static final Logger logger = LoggerFactory.getLogger(RequestGetReader.class);
    static final String URL_REGEX = "(/[^\\s]+)";


    static String findPathInRequest(HttpRequest httpRequest) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(httpRequest.getHeaderContents().get(HttpRequest.REQUEST_LINE));
        String url = null;
        if (matcher.find()) {
            url = matcher.group(1); // Extract URL from first group
        }
        return url;
    }


    byte[] readData(HttpRequest httpRequest);


    static RequestReader selectRequestReaderByMethod(HttpMethod httpMethod) throws ProtocolException {
        if (httpMethod.equals(HttpMethod.GET)) {
            return new RequestGetReader();
        } else {
            logger.error(HttpsErrorMessage.NOT_VALID_HTTP_FORMAT);
            throw new ProtocolException(HttpsErrorMessage.NOT_VALID_HTTP_FORMAT);
        }
    }
}
