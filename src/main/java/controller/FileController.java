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

    public HttpResponse TemplateController(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = new byte[0];
        data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse(new Data(dataOutputStream, data), FileType.getFileType(httpRequest.getUrl()), HttpStatus.OK);
    }

    public HttpResponse StaticController(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new StaticFileReader();
        byte[] data = new byte[0];
        data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse(new Data(dataOutputStream, data), FileType.getFileType(httpRequest.getUrl()), HttpStatus.OK);
    }



}
