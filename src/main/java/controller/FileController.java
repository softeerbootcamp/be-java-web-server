package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.fileReader.FileReader;
import reader.fileReader.StaticFileReader;
import reader.fileReader.TemplatesFileReader;
import request.HttpRequest;
import response.Data;
import response.HttpResponse;
import util.FileType;
import util.HttpStatus;
import util.error.HttpsErrorMessage;

import java.io.DataOutputStream;
import java.io.IOException;

public class FileController  implements  Controller{

    Logger logger = LoggerFactory.getLogger(FileController.class);

    FileReader fileReader;

    public HttpResponse TemplateController(DataOutputStream dataOutputStream, HttpRequest httpRequest) {
        fileReader = new TemplatesFileReader();
        byte[] data = new byte[0];
        try {
            data = fileReader.readFile(httpRequest.getUrl());
        } catch (IOException e) {
            logger.error(HttpsErrorMessage.NOT_FOUND_FILE);
            return new HttpResponse(new Data(dataOutputStream, data), FileType.getFileType(httpRequest.getUrl()), HttpStatus.NOT_FOUND);
        }
        return new HttpResponse(new Data(dataOutputStream, data), FileType.getFileType(httpRequest.getUrl()), HttpStatus.OK);
    }

    public HttpResponse StaticController(DataOutputStream dataOutputStream, HttpRequest httpRequest) {
        fileReader = new StaticFileReader();
        byte[] data = new byte[0];
        try {
            data = fileReader.readFile(httpRequest.getUrl());
        } catch (IOException e) {
            logger.error(HttpsErrorMessage.NOT_FOUND_FILE);
            return new HttpResponse(new Data(dataOutputStream, data), FileType.getFileType(httpRequest.getUrl()), HttpStatus.NOT_FOUND);
        }
        return new HttpResponse(new Data(dataOutputStream, data), FileType.getFileType(httpRequest.getUrl()), HttpStatus.OK);
    }



}
