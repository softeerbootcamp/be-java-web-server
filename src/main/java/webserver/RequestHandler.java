package webserver;

import java.io.*;
import java.net.Socket;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtil;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String ABSOLUTE_PATH = "/Users/rentalhub/HMG/be-java-web-server/src/main/resources";
    private static final String TEMPLATES = "/templates";
    private static final String STATIC = "/static";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
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
            RequestHeaderMessage requestHeaderMessage = new RequestHeaderMessage(br.readLine());
            //String httpHeader = br.readLine();
            //String[] headers = httpHeader.split(" ");
            //String httpMethod = headers[0];
            //String reqURL = headers[1].equals("/")?"/index.html":headers[1];
            reqURL = createUser(reqURL);
            reqURL = HttpRequestUtil.getOnlyURL(reqURL);    //url without parameter
            String fileExtension = HttpRequestUtil.getFileExtension(reqURL);
            //String httpVersion = headers[2];
            String strBr = "";
            while(!(strBr = br.readLine()).equals("")){
                System.out.println(strBr);
            }
            String fileURL = ABSOLUTE_PATH;
            String contentType = fileExtension.equals("html")?"text/html":fileExtension.equals("css")
                    ?"text/css":"text/javascript";
            fileURL += (fileExtension.equals("html")||fileExtension.equals("ico"))?TEMPLATES:STATIC;
            byte[] body = Files.readAllBytes(new File(fileURL+reqURL).toPath());
            // TODO 사용자 요청에 대한 처리는 이 곳까지 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length,contentType);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String createUser(String reqURL){
        if (reqURL.contains("create")){
            Map<String,String> userInfo = HttpRequestUtil.parseQueryString(reqURL);
            Database.addUser(new User(userInfo.get(USER_ID),userInfo.get(PASSWORD),userInfo.get(NAME),userInfo.get(EMAIL)));
            Stream.of(userInfo.entrySet()).forEach(System.out::println);
            reqURL = "/index.html";
        }
        return reqURL;
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, String contentType){

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

    private void response302Header(DataOutputStream dos, int lengthOfBodyContent, String contentType, String redirectURL) {
        try{
            dos.writeBytes("HTTP/1.1 301\r\n");
            dos.writeBytes("Location:localhost:8080"+redirectURL+"\r\n");
            dos.writeBytes("Content-Type: "+contentType+";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        }catch (IOException e){
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
