package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String ABSOLUTE_PATH = "/Users/rentalhub/HMG/be-java-web-server/src/main/resources";
    private static final String TEMPLATES = "/templates";
    private static final String STATIC = "/static";
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
            String fileExtension = reqURL.substring(reqURL.lastIndexOf('.')+1);
            String httpVersion = headers[2];
            System.out.println("Http Method: "+httpMethod);
            System.out.println("Http reqURL: "+reqURL);
            System.out.println("Http Version: "+httpVersion);
            String strBr = "";
            while(!(strBr = br.readLine()).equals("")){
                System.out.println(strBr);
            }
            String fileURL = ABSOLUTE_PATH;
            String contentType = fileExtension.equals("html")?"text/html":"text/css";
            fileURL += fileExtension.equals("html")?TEMPLATES:STATIC;
            byte[] body = Files.readAllBytes(new File(fileURL+reqURL).toPath());
            // TODO 사용자 요청에 대한 처리는 이 곳까지 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            //byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length,contentType);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: "+contentType+";charset=utf-8\r\n");
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
