package webserver;

import model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final String BASE_URL = "/index.html";

    private final Socket connection;
    private final UserService userService;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.userService = new UserService();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            Request request = makeRequest(br.readLine());

            if (request.getRequestParams().size() != 0) {
                userService.signUpUser(request);
            }

            ResponseHandler responseHandler = new ResponseHandler(connection);
            responseHandler.response(request);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String extractUrl(String line) {
        //문자열 분리 후 -> 문자열 배열에 삽입
        String[] tokens = line.split(" ");
        String url = tokens[1];
        if (url.equals("/")) {
            url = BASE_URL;
        }
        return url;
    }

    public Request makeRequest(String line) {
        logger.debug("> request line : {}", line);
        String url = extractUrl(line);
        Request request = new Request(url);
        request.checkUrlQueryString();

        return request;
    }
}
