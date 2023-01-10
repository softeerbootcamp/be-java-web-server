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

            byte[] data = readData(httpRequest);


            DataOutputStream clientOutPutStream = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse(clientOutPutStream,data);
            httpResponse.makeResponse();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] readData(HttpRequest httpRequest) {
        byte[] data=null;
        fileReader = FileReader.selectFileReader(httpRequest.getUrl());
        if(fileReader!=null){
            //index.html과 같은 파일을 읽는 경우
            data = fileReader.readFile(httpRequest.getUrl());
        }else{
            data = requestReader.readData(httpRequest);
        }
        return data;
    }
}

