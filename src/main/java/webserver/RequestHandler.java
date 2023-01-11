package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpUtils.RequestParser;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import static webserver.Paths.TEMPLATE_PATH;
import static webserver.Paths.STATIC_PATH;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Map<String, String> reqLine;
    private String reqHeader;
    private String reqBody;

    private Map<String, String> resLine;
    private String resHeader;
    private String resBody;

    private Socket connection;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            reqLine = RequestParser.parseRequestLine(in);
            String reqMethod = reqLine.get(RequestParser.METHOD);
            String reqQuery = reqLine.get(RequestParser.QUERY);
            String reqVersion = reqLine.get(RequestParser.VERSION);

            if(reqQuery.contains("/create") && reqMethod.equals("GET"))
            {
                // GET 방식의 회원가입 처리
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
