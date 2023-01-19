package controller;

import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.fileReader.FileReader;
import reader.fileReader.StaticFileReader;
import reader.fileReader.TemplatesFileReader;
import request.HttpRequest;
import request.Url;
import response.Data;
import response.HttpResponse;
import util.FileType;
import util.HttpMethod;
import util.HttpStatus;
import request.RequestDataType;

import java.io.DataOutputStream;
import java.io.IOException;

@ControllerInfo
public class FileController  implements  Controller{

    Logger logger = LoggerFactory.getLogger(FileController.class);

    FileReader fileReader;


    @ControllerMethodInfo(path =".*\\.(css|js|tff|woff)$", method = HttpMethod.GET)
    public HttpResponse StaticController(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new StaticFileReader();
        byte[] data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream,data))
                .setFileType(FileType.getFileType(httpRequest.getUrl()))
                .setHttpStatus(HttpStatus.OK)
                .build();
    }



}
