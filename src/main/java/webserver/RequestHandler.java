package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.RequestReader;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import request.HttpRequest;
import response.HttpResponse;
import util.HttpStatus;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestReader requestReader;
    private FileReader fileReader;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.getHttpRequest(in);

            requestReader = RequestReader.selectRequestReaderByMethod(httpRequest.getHttpMethod());
            String url = requestReader.findPathInRequest(httpRequest);

            fileReader = FileReader.selectFileReader(url);
            byte[] data = fileReader.readFile(url);

            DataOutputStream clientOutPutStream = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse(clientOutPutStream,data);
            httpResponse.makeResponse();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

