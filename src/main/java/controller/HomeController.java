package controller;

import controller.annotation.ControllerInfo;
import controller.annotation.ControllerMethodInfo;
import db.SessionDatabase;
import model.Session;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import request.HttpRequest;
import request.RequestDataType;
import response.Data;
import response.HttpResponse;
import service.SessionService;
import util.Cookie;
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
    private SessionDatabase sessionDatabase = new SessionDatabase();

    private HomeRender homeRender = HomeRender.getInstance();

    @ControllerMethodInfo(path = "/index.html", type = RequestDataType.TEMPLATES_FILE, method = HttpMethod.GET)
    public HttpResponse home(DataOutputStream dataOutputStream, HttpRequest httpRequest) throws IOException {
        fileReader = new TemplatesFileReader();
        byte[] data ;
        data = fileReader.readFile(httpRequest.getUrl());
        data = updateHtmlUsingSession(httpRequest, data);
        return new HttpResponse.Builder()
                .setData(new Data(dataOutputStream,data))
                .setFileType(FileType.HTML)
                .setHttpStatus(HttpStatus.OK)
                .build();
    }

    private byte[] updateHtmlUsingSession(HttpRequest httpRequest, byte[] htmlData) {
        if (sessionService.cookieValidInSession(httpRequest.getRequestHeader())) {
            Cookie cookie = Cookie.extractCookie(httpRequest.getRequestHeader()).get();
            Session session = sessionDatabase.findObjectById(cookie.getValue()).get();
            htmlData = homeRender.addUserName(htmlData,session.getUser().getName());
        }else{
            htmlData = homeRender.addSignInAndUpTag(htmlData);
        }
        return htmlData;
    }
}
