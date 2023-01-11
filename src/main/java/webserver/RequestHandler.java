package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static util.HttpRequestUtils.parseQuerystring;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final UserService userService;
    private final ViewResolver viewResolver;

    private Socket connection;
    private final String BASE_URL = "/index.html";

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.userService = new UserService();
        this.viewResolver = new ViewResolver();
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

            //응답 메시지 출력 준비
            DataOutputStream dos = new DataOutputStream(out);

            if (url.contains("?")) {
                response302Header(dos);
            } else {
                Path path = viewResolver.findFilePath(url);
                String contentType = Files.probeContentType(path);
                byte[] body = viewResolver.findActualFile(path);
                response200Header(dos, body.length, contentType);
                responseBody(dos, body);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
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

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
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

    private void printHeaders(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();
        logger.debug("request line : {}", line);
        while (!line.equals("")) {
            line = br.readLine();
            logger.debug("header : {}", line);
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
