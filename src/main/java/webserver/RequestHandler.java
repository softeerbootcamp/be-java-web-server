package webserver;

import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String CRLF = "\r\n";
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            CustomHttpRequest req = CustomHttpRequest.from(in);
            RequestRouter requestRouter = RequestRouter.getRequestRouter();

            CustomHttpResponse res = requestRouter.handleRequest(req);

            sendResponse(res, out, req.getProtocolVersion());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendResponse(CustomHttpResponse response, OutputStream out, String protocolVersion) {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes(response.getStatusLine(protocolVersion) + CRLF);
            dos.writeBytes(response.getContentTypeLine() + CRLF);
            for (String key : response.getHeaders().keySet()) {
                dos.writeBytes(key + ": " + response.getHeaders().get(key) + CRLF);
            }
            dos.writeBytes("Content-Length: " + response.getBody().length + CRLF);
            dos.writeBytes(CRLF);

            dos.write(response.getBody(), 0, response.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error("While writing response " + e.getMessage());
        }
    }

}
