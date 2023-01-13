package request;

import java.io.InputStream;

public interface RequestHandler {
    byte[] handleRequest(Request request, int port);
}
