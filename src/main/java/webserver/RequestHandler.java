package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import response.Response;
import utils.RequestUtils;

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
            Response response = new Response();
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String line = br.readLine();
            String path = RequestUtils.getFilePathByRequest(line);
            RequestUtils.printRequestLines(line,br);
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.


            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File("./src/main/resources/templates"+path).toPath());
            response.response200Header(dos, body.length);
            response.responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
