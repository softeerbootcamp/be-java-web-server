package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestResponseHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseBody {
    private byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }
}
