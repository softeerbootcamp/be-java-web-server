package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import static util.HttpRequestUtils.parseQuerystring;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final UserService userService;

    private Socket connection;
    private final String BASE_URL = "/index.html";

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
            String url = getUrl(line);
            logger.debug("> request url : {}", url);

            url = checkUrlQueryString(url);

            DataOutputStream dos = new DataOutputStream(out);

            byte[] body = response200HeaderByExtension(url, dos);

            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    //TODO 리팩토링 필요!! src/main/resources/static/user/js/scripts.js 이렇게 옴.
    private byte[] response200HeaderByExtension(String url, DataOutputStream dos) throws IOException {
        String substring = url.substring(url.lastIndexOf(".")+1);
        logger.debug(">>> substring : {}", substring);

        byte[] body;
        if (substring.equals("html") || substring.equals("ico")) {
            body = Files.readAllBytes(new File(ViewResolver.TEMPLATES_PATH + url).toPath());
            response200Header(dos, body.length, "text/html");
        } else{
            body = Files.readAllBytes(new File(ViewResolver.STATIC_PATH + url).toPath());
            if (substring.equals("css")) {
                response200Header(dos, body.length, "text/css");
            } else {
                response200Header(dos, body.length, "text/javascript");
            }
        }
        return body;
    }

    private String checkUrlQueryString(String url) {
        String[] queryStrings = url.split("\\?");
        if (queryStrings.length != 1) {
            String queryString = queryStrings[1];
            logger.debug(">> {}", queryString);

            Map<String, String> requestParams = parseQuerystring(queryString);
            userService.signUpUser(requestParams);

            url = BASE_URL;
        }
        return url;
    }


    private String getUrl(String line) {
        //문자열 분리 후 -> 문자열 배열에 삽입
        String[] tokens = line.split(" ");
        String url = tokens[1];
        if (url.equals("/")) {
            url = BASE_URL;
        }
        return url;
    }

    private void printHeaders(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();
        logger.debug("request line : {}", line);
        while (!line.equals("")) {
            line = br.readLine();
            logger.debug("header : {}", line);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType+";charset=utf-8\r\n");
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
