package controller;

import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import request.HttpRequest;
import request.RequestDataType;
import response.Data;
import response.HttpResponse;
import service.SessionService;
import util.FileType;
import util.HttpMethod;
import util.HttpStatus;
import view.HomeRender;

import java.io.DataOutputStream;
import java.io.IOException;

@ControllerInfo
public class HomeController implements Controller {
    private FileReader fileReader;

    private SessionService sessionService = new SessionService();

    private HomeRender homeRender = HomeRender.getInstance();

    @ControllerMethodInfo(path = "/index.html", type = RequestDataType.TEMPLATES_FILE, method = HttpMethod.GET)
    public HttpResponse home(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {


        fileReader = new TemplatesFileReader();
        byte[] data = new byte[0];
        data = fileReader.readFile(httpRequest.getUrl());

        //로그인 확인
        if (!sessionService.cookieValidInSession(httpRequest.getRequestHeader())) {
            data = homeRender.addSignInAndUpTag(new String(data));
        }

        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream,data))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.OK)
                .build();
    }
}
