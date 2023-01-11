package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Stream;

import model.repository.MemoryUserRepository;
import model.domain.User;
import model.repository.UserRepository;
import model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtil;
import util.Redirect;
import view.InputView;
import view.OutputView;

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
    UserService userService = new UserService(new MemoryUserRepository());
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

            String httpOnlyURL = requestHeaderMessage.getHttpOnlyURL();
            String requestAttribute = "";
            if (httpOnlyURL.contains("user")){    // /user/? 명령이 올 때 ?에 따른 명령 처리
                userCommand(requestHeaderMessage);
            }

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
            //logMembers();
            DataOutputStream dos = new DataOutputStream(out);
            responseHeader(dos,body.length,contentType, requestHeaderMessage.getHttpOnlyURL());
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void userCommand(RequestHeaderMessage requestHeaderMessage) {
        if (requestHeaderMessage.getRequestAttribute().equals("/create")){
            Map<String,String> userInfo = HttpRequestUtil.parseQueryString(requestHeaderMessage.getHttpReqParams());
            userService.join(new User(userInfo.get(USER_ID),userInfo.get(PASSWORD),userInfo.get(NAME),userInfo.get(EMAIL)));
        }
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, String contentType, String onlyURL){
        String redirectLink = Redirect.getRedirectLink(onlyURL);
        if (redirectLink.equals(""))
            response200Header(dos,lengthOfBodyContent,contentType);
        else response302Header(dos, redirectLink);
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

    private void response302Header(DataOutputStream dos, String redirectLink) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
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

    /*
    private void logMembers(){
        Stream.of(userRepository.findAll()).forEach(user->logger.info(user.toString()));
    }

     */
}
