package webserver;

import io.request.Request;
import io.request.RequestFactory;
import io.response.Response;
import io.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class FacadeController implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(FacadeController.class);
    private final RequestFactory requestFactory = new RequestFactory();
    private final ResponseFactory responseFactory = new ResponseFactory();

    private Socket connection;

    public FacadeController(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = requestFactory.create(in);
            Response response = responseFactory.create(request, new DataOutputStream(out));
            response.send();
            // byte[] file = Files.readAllBytes(new File(String.format(ABSOLUTE_PATH, resourcePath)).toPath());
            // DataOutputStream dos = new DataOutputStream(out);
            // response200Header(dos, file.length);
            // responseBody(dos, file);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getRequestMessage(BufferedReader bufferedReader) throws IOException {
        String msg = "";
        String line;
        while (!(line = bufferedReader.readLine()).isEmpty()) {
            msg += line + "\n\r";
        }

        return msg;
    }

    private String getRequestUrl(BufferedReader br) throws IOException {
        return br.readLine().split(" ")[1];
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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
