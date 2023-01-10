package webserver;

import java.io.*;
import java.net.Socket;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;
import utils.Utilites;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() { //즉 이미 클라이언트와 연결이 된 상태에서 돌아가는 메서드임
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            //브라우저에서 서버로 들어오는 모든 요청은 Inputstream 안에 담겨져 있음, outputstream은 서버에서 브라우저로 보내는 응답
            String url = RequestParser.parseRequestStartLineTarget(in);
            if(url.startsWith("/user/create")){
                //user/create?userId=user&password=66a41ad2-bda9-4c70-807c-e0e13ff04475&name=1234&email=1234%40khu.ac.kr
                String[] queries  = Utilites.stringParser(url,"\\?");
                Map<String,String> signUpUserQueries = HttpRequestUtils.parseQueryString(queries[1]);
                String userId = signUpUserQueries.get("userId");
                String password = signUpUserQueries.get("password");
                String name = signUpUserQueries.get("name");
                String email = signUpUserQueries.get("email");//1234%40khu.ac.kr
                email.replace("%40","@");
                User user = new User(userId,password,name,email);
                System.out.println(user.toString());
            }
            ResponseWriter rw = new ResponseWriter(out);
            rw.write(url);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
