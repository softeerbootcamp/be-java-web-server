package webserver;

import controller.ControllerFactory;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            String headers = extractRequestHeaders(br);
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequest.from(headers);
            HttpResponse httpResponse = new HttpResponse(dos);

            ControllerFactory.handle(httpRequest, httpResponse);


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String extractRequestHeaders(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        while (Objects.nonNull(line = br.readLine()) && !line.isEmpty()) {
            sb.append(line).append(System.lineSeparator());
        }

        return sb.toString();
    }

}
