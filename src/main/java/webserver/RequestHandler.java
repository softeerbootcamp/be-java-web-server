package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);

            String url = getUrl(in);
            System.out.println(url);
            if(url.contains("/user/create"))
            {
                SignUpController.enrollNewUser(url);
                url = SignUpController.redirectToIndex();
                // html 파일이 아닌 파일들도 정상적으로 처리될 수 있게 구현하기
            }
            String contentType = Files.probeContentType(new File(url).toPath());

            byte[] bytes = Byte.urlToByte(url);

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
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getUrl(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String firstLine = br.readLine();
        System.out.println(firstLine);
        String[] splitedFirstLine = firstLine.split(" ");
        return splitedFirstLine[1];
    }
}

class Byte {
    static byte[] urlToByte(String url) throws IOException {
        if (url.contains("html") || url.contains("favicon"))
            return Files.readAllBytes(new File("./src/main/resources/templates" + url).toPath());
        else
            return Files.readAllBytes(new File("./src/main/resources/static" + url).toPath());
    }
}
