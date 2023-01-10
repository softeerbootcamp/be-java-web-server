package request;

import java.io.DataOutputStream;
import java.io.InputStream;

public interface RequestHandler {
    void handleRequest(InputStream in, DataOutputStream dos, int port);
}
