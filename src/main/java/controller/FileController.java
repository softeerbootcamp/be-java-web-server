package controller;

import controller.annotation.ControllerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.fileReader.FileReader;
import reader.fileReader.StaticFileReader;
import reader.fileReader.TemplatesFileReader;
import request.HttpRequest;
import response.Data;
import response.HttpResponse;
import util.FileType;
import util.HttpMethod;
import util.HttpStatus;
import util.UrlType;

import java.io.DataOutputStream;
import java.io.IOException;

public class FileController  implements  Controller{

    Logger logger = LoggerFactory.getLogger(FileController.class);

    FileReader fileReader;

    @ControllerInfo(path = "", u = UrlType.TEMPLATES_FILE, method = HttpMethod.GET)
    public HttpResponse TemplateController(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = new byte[0];
        data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse(new Data(dataOutputStream, data), FileType.getFileType(httpRequest.getUrl()), HttpStatus.OK);
    }

    @ControllerInfo(path = "", u = UrlType.STATIC_FILE, method = HttpMethod.GET)
    public HttpResponse StaticController(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new StaticFileReader();
        byte[] data = new byte[0];
        data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse(new Data(dataOutputStream, data), FileType.getFileType(httpRequest.getUrl()), HttpStatus.OK);
    }



}
