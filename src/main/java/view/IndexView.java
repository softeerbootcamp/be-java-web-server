package view;

import request.HttpRequest;
import db.DBConnector;
import model.Qna;
import model.User;
import repository.QnaRepository;
import util.HtmlBuildUtil;
import util.LoginUtil;

import java.util.List;
import java.util.Objects;

public class IndexView implements View{
    private static IndexView indexView;

    public static IndexView getInstance() {
        if(Objects.isNull(indexView)){
            indexView = new IndexView();
        }
        return indexView;
    }
    @Override
    public byte[] render(HttpRequest httpRequest) {
        List<Qna> qnas = new QnaRepository().find10Qna(DBConnector.connect());
        String html = HtmlBuildUtil.buildQnaList(qnas);
        try {
            User user = LoginUtil.checkSession(httpRequest);
            return HtmlBuildUtil.withoutLoginWithUserName(html, "/index.html", user);
        }catch (NullPointerException e){
            return html.getBytes();
        }
    }
}
