package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import model.repository.MemoryUserRepository;
import model.domain.User;
import model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtil;
import util.HttpStatus;
import util.Redirect;
import view.RequestHeaderMessage;
import view.Response;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String RELATIVE_PATH = "./src/main/resources";
    private static final String TEMPLATES = "/templates";
    private static final String STATIC = "/static";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private HttpStatus httpStatus;
    private Socket connection;
    Map<String,String> headerKV= new HashMap<>();
    UserService userService = new UserService(new MemoryUserRepository());
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            httpStatus = HttpStatus.ClientError;
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            RequestHeaderMessage requestHeaderMessage = new RequestHeaderMessage(br.readLine());
            if (requestHeaderMessage.getHttpOnlyURL().contains("user")){
                userCommand(requestHeaderMessage);
            }
            while(!(br.readLine()).equals("")){}
            byte[] body = getBodyFile(requestHeaderMessage);
            Response response = new Response(new DataOutputStream(out));
            response.response(body,requestHeaderMessage, httpStatus, headerKV);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] getBodyFile(RequestHeaderMessage requestHeaderMessage){
        String fileURL = RELATIVE_PATH+ (requestHeaderMessage.getFileExtension().equals("html")?TEMPLATES:STATIC);
        logger.debug(fileURL+requestHeaderMessage.getHttpOnlyURL());
        if (requestHeaderMessage.getRequestAttribute().contains(".")) {  //파일을 요청 (.html, .js, .css etc..)
            try {
                httpStatus = HttpStatus.Success;
                return Files.readAllBytes(new File(fileURL+requestHeaderMessage.getHttpOnlyURL()).toPath());
            } catch (IOException e){
                logger.debug(e.getMessage());
            }
        }
        return new byte[0];
    }

    private void userCommand(RequestHeaderMessage requestHeaderMessage) {
        if (requestHeaderMessage.getRequestAttribute().equals("/create")){
            try{
                userCreate(requestHeaderMessage);
                setLocation(Redirect.getRedirectLink(requestHeaderMessage.getHttpOnlyURL()));
            } catch (IllegalStateException e){
                setLocation("/user/form.html");
                logger.debug(e.getMessage());
            }
        }
    }

    private void setLocation(String redirectLink){
        if (!redirectLink.equals("")){
            httpStatus = HttpStatus.Redirection;
            headerKV.put("Location",redirectLink);
        }
    }

    private void userCreate(RequestHeaderMessage requestHeaderMessage){
        Map<String,String> userInfo = HttpRequestUtil.parseQueryString(requestHeaderMessage.getHttpReqParams());
        userService.join(new User(userInfo.get(USER_ID),userInfo.get(PASSWORD),userInfo.get(NAME),userInfo.get(EMAIL)));
        Stream.of(userService.findUsers()).forEach(user->logger.debug(user.toString()));
    }


}
