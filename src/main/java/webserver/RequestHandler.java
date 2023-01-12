package webserver;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import db.Database;
import db.UserDatabase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.RequestReader;
import reader.fileReader.FileReader;
import request.HttpRequest;
import response.HttpResponse;
import service.Service;
import service.UserService;
import util.FileType;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestReader requestReader;
    private FileReader fileReader;

    private Database database;

    private Service service;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.getHttpRequest(in);
            requestReader = RequestReader.selectRequestReaderByMethod(httpRequest.getHttpMethod());

            byte[] data = manageData(httpRequest);

            DataOutputStream clientOutPutStream = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse(clientOutPutStream,data, FileType.getFileType(httpRequest.getUrl()));
            httpResponse.makeResponse();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] manageData(HttpRequest httpRequest) {
        byte[] data=null;
        fileReader = FileReader.selectFileReader(httpRequest.getUrl());
        if(fileReader!=null){
            //index.html과 같은 파일을 읽는 경우
            data = fileReader.readFile(httpRequest.getUrl());
        }else{
            HashMap<String, String> dataMap = requestReader.readData(httpRequest);
            database = new UserDatabase();
            service = new UserService();
            Object model = service.createModel(dataMap);
            database.addData(model);
            System.out.println("database = " + database.findAll());
        }
        return data;
    }
}

