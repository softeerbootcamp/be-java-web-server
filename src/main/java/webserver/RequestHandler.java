package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String ABSOLUTE_PATH = "/Users/rentalhub/HMG/be-java-web-server/src/main/resources/templates";
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳부터 구현하면 된다.
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            String httpHeader = br.readLine();
            String[] headers = httpHeader.split(" ");
            String httpMethod = headers[0];
            String reqURL = headers[1];
            if (reqURL.equals("/"))
                reqURL = "/index.html";
            String httpVersion = headers[2];
            System.out.println("Http Method: "+httpMethod);
            System.out.println("Http reqURL: "+reqURL);
            System.out.println("Http Version: "+httpVersion);
            String strBr = "";
            while(!(strBr = br.readLine()).equals("")){
                System.out.println(strBr);
            }
            byte[] body = Files.readAllBytes(new File(ABSOLUTE_PATH+reqURL).toPath());
            // TODO 사용자 요청에 대한 처리는 이 곳까지 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            //byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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
}
