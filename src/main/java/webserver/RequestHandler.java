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
import util.Redirect;

import javax.xml.crypto.Data;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String RELATIVE_PATH = "./src/main/resources";
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
            createUser(requestHeaderMessage);
            String strBr = "";
            while(!(strBr = br.readLine()).equals("")){
                //System.out.println(strBr);
            }
            String fileURL = RELATIVE_PATH;
            String fileExtension = requestHeaderMessage.getFileExtension();
            String contentType = "text/" + (fileExtension.equals("js")?"javascript":fileExtension);
            fileURL += (fileExtension.equals("html")||fileExtension.equals("ico"))?TEMPLATES:STATIC;
            byte[] body = new byte[0];
            if (!requestHeaderMessage.getHttpOnlyURL().contains("create"))
                body = Files.readAllBytes(new File(fileURL+requestHeaderMessage.getHttpOnlyURL()).toPath());
            // TODO 사용자 요청에 대한 처리는 이 곳까지 구현하면 된다.
            logMembers();
            DataOutputStream dos = new DataOutputStream(out);
            responseHeader(dos,body.length,contentType, requestHeaderMessage.getHttpOnlyURL());
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, String contentType, String onlyURL){
        String redirectLink = Redirect.getRedirectLink(onlyURL);
        if (redirectLink.equals(""))
            response200Header(dos,lengthOfBodyContent,contentType);
        else response301Header(dos, redirectLink);
    }

    private boolean createUser(RequestHeaderMessage requestHeaderMessage){
        if (requestHeaderMessage.getHttpOnlyURL().contains("create")){
            Map<String,String> userInfo = HttpRequestUtil.parseQueryString(requestHeaderMessage.getHttpReqParams());
            Database.addUser(new User(userInfo.get(USER_ID),userInfo.get(PASSWORD),userInfo.get(NAME),userInfo.get(EMAIL)));
            //requestHeaderMessage.isRedirection();
            return true;
        }
        return false;
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

    private void response301Header(DataOutputStream dos, String redirectLink) {
        try {
            dos.writeBytes("HTTP/1.1 301 FOUND \r\n");
            dos.writeBytes("Location: "+redirectLink+"\r\n");
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

    private void logMembers(){
        Stream.of(Database.findAll()).forEach(user->logger.info(user.toString()));
    }
}
