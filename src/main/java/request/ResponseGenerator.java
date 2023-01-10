package request;

import java.io.InputStream;

public interface ResponseGenerator {
    byte[] generate(InputStream in, int port);
}
