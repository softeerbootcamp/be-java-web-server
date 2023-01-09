package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.utils.FilePathParser;
import webserver.utils.RequestParser;

import static webserver.utils.FilePathParser.getStaticFilesPath;
import static webserver.utils.FilePathParser.getTemplateFilesPath;
import static webserver.utils.RequestParser.parseRequestedHeader;
import static webserver.utils.RequestParser.parseRequestedStatic;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestParser requestParser;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public byte[] staticResolver(String fileName) throws IOException{
        String[] type = fileName.split("\\.");
        String typeName = type[type.length - 1];
        if(typeName.equals("html") || typeName.equals("ico"))
            return getTemplateFilesPath(fileName);
        else
            return getStaticFilesPath(fileName);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            String fileName = parseRequestedStatic(line)[1];
            line = br.readLine();
            System.out.println("line : " + line);

            while(!line.equals("")){
                logger.debug("body : {}", line);
                Map<String, String> reqMap = new HashMap<>();
                String[] parsedReq = parseRequestedHeader(line);
                reqMap.put(parsedReq[0], parsedReq[1]);
                line = br.readLine();
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = staticResolver(fileName);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
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
