package controller;

import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import model.User;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import reader.requestReader.RequestPostReader;
import request.HttpRequest;
import request.RequestDataType;
import request.Url;
import response.Data;
import response.HttpResponse;
import util.Cookie;
import util.FileType;
import util.HttpMethod;
import util.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

@ControllerInfo
public class BoardController implements Controller {
    private FileReader fileReader;
    @ControllerMethodInfo(path = "/qna/form.html", method = HttpMethod.GET)
    public HttpResponse qnaFormHtml(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream, data))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.OK)
                .build();
    }
}
