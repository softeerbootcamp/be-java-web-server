package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.utils.RequestLineParser;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import static webserver.Paths.TEMPLATE_PATH;
import static webserver.Paths.STATIC_PATH;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            // TODO request line parser 구현하기
            Map<String, String> requestLine = RequestLineParser.parseRequestLine(in);
            String reqMethod = requestLine.get(RequestLineParser.METHOD);
            String reqQuery = requestLine.get(RequestLineParser.QUERY);
            String reqVersion = requestLine.get(RequestLineParser.VERSION);

            if(reqQuery.contains("/create"))
            {
                SignUpController.enrollNewUser(reqQuery);
                reqQuery = SignUpController.redirectToIndex();
            }
            String contentType = Files.probeContentType(new File(reqQuery).toPath());

            byte[] bytes = Byte.urlToByte(reqQuery);

            response200Header(dos, bytes.length, contentType);
            responseBody(dos, bytes);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        }catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}

class Byte {
    static byte[] urlToByte(String url) throws IOException {
        if (url.contains("html") || url.contains("favicon"))
            return Files.readAllBytes(new File(TEMPLATE_PATH.getPath() + url).toPath());
        else
            return Files.readAllBytes(new File(STATIC_PATH.getPath() + url).toPath());
    }
}
