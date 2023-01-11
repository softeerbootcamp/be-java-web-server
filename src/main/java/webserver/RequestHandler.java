package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import static util.HttpRequestUtils.parseQuerystring;

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

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            logger.debug("> input line : {}", line);
            String url = extractUrl(line);
            checkUrlQueryString(url);

            ResponseHandler responseHandler = new ResponseHandler(connection);
            responseHandler.response(url);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void checkUrlQueryString(String url) {
        String[] queryStrings = url.split("\\?");
        if (queryStrings.length != 1) {
            String queryString = queryStrings[1];

            Map<String, String> requestParams = parseQuerystring(queryString);
            userService.signUpUser(requestParams);
        }
    }

    private String extractUrl(String line) {
        //문자열 분리 후 -> 문자열 배열에 삽입
        String[] tokens = line.split(" ");
        String url = tokens[1];
        if (url.equals("/")) {
            url = BASE_URL;
        }
        return url;
    }
}
