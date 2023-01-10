package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.google.common.primitives.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            CustomHttpRequest req = new CustomHttpRequest(in);
            CustomHttpResponse res = new CustomHttpResponse(out);
            RequestRouter requestRouter = RequestRouter.getRequestRouter();
            requestRouter.doRoute(req, res);
            response(res);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response(CustomHttpResponse response) {
        try {
            DataOutputStream dos = new DataOutputStream(response.getOutputStream());
            dos.writeBytes(response.getProtocolVersion()+" " + response.getStatusCode().getCode() + " " + response.getStatusCode().getMessage() + "\r\n");
            dos.writeBytes("Content-Type: " + response.getContentType().getContentType() + "; charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + response.getBody().size() + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(Bytes.toArray(response.getBody()), 0, response.getBody().size());
            dos.flush();
        } catch (IOException e) {
            logger.error("While writing response " + e.getMessage());
        }
    }

}
