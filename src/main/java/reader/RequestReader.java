package reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpMethod;
import request.HttpRequest;
import util.error.HttpsErrorMessage;

import java.net.ProtocolException;

public interface RequestReader {
     static final Logger logger = LoggerFactory.getLogger(RequestGetReader.class);
    String findPathInRequest(HttpRequest httpRequest);





    static RequestReader selectRequestReaderByMethod(HttpMethod httpMethod) throws ProtocolException {
        if (httpMethod.equals(HttpMethod.GET)) {
            return new RequestGetReader();
        } else {
            logger.error(HttpsErrorMessage.NOT_VALID_HTTP_FORMAT);
            throw new ProtocolException(HttpsErrorMessage.NOT_VALID_HTTP_FORMAT);
        }
    }
}
