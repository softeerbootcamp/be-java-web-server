package controller;

import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import model.User;
import org.checkerframework.checker.units.qual.C;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import reader.requestReader.RequestPostReader;
import request.HttpRequest;
import request.RequestDataType;
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
public class HomeController implements Controller {
    FileReader fileReader;

    @ControllerMethodInfo(path = "/index.html", type = RequestDataType.TEMPLATES_FILE, method = HttpMethod.GET)
    public HttpResponse home(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data = new byte[0];
        data = fileReader.readFile(httpRequest.getUrl());
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream,data))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.OK)
                .build();
    }
}
