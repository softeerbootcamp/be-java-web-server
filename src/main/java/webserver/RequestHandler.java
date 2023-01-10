package webserver;

import db.Database;
import http.*;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            HttpRequest httpRequest = HttpRequest.from(br);
            HttpResponse httpResponse = new HttpResponse(dos);
            RequestLine requestLine = httpRequest.getRequestLine();
            Uri uri = requestLine.getUri();
            if (uri.getPath().equals("/user/create")) {
                QueryParameters queryParameters = uri.getQueryParameters();
                Database.addUser(User.from(queryParameters.getParameters()));
            } else {
                byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + requestLine.getUri().getPath()).toPath());
                httpResponse.response200Header(body.length);
                httpResponse.responseBody( body);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
