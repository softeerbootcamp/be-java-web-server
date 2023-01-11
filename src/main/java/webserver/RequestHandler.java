package webserver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.net.Socket;

import controller.Controller;
import util.ControllerMapper;
import model.response.Response;
import model.request.RequestLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();

            DataOutputStream dos = new DataOutputStream(out);
            RequestLine requestLine = RequestLine.of(line);

            Controller controller = ControllerMapper.selectController(requestLine);
            Response response = controller.getResponse(requestLine);

            response.writeOutputStream(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
