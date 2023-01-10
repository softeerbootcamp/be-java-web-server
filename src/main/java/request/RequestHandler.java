package request;

import java.io.DataOutputStream;
import java.io.InputStream;

public interface ResponseGenerator {
    void generate(InputStream in, DataOutputStream dos, int port);
}
