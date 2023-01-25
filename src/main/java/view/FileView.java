package view;

import Request.HttpRequest;
import model.User;
import util.HtmlBuildUtil;
import util.HttpResponseUtil;
import util.LoginUtil;

import java.util.Objects;

public class FileView implements View{
    private static FileView fileView = null;
    public static FileView getInstance(){
        if(Objects.isNull(fileView)){
            synchronized (FileView.class){
                fileView = new FileView();
            };
        }
        return fileView;
    }
    @Override
    public byte[] render(HttpRequest httpRequest) {
        try {
            User user = LoginUtil.checkSession(httpRequest);
            return HtmlBuildUtil.buildHtml(httpRequest.getPath(), user);
        } catch (NullPointerException e) {
            return HttpResponseUtil.generateBytesBody(httpRequest.getPath());
        }
    }
}
