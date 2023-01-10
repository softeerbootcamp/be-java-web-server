package Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private byte[] body;
    DataOutputStream dos;

    public HttpResponse(byte[] body) {
        this.body = body;
    }

}
