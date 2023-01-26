package view;

import Request.HttpRequest;
import db.DBConnector;
import model.Qna;
import model.User;
import repository.QnaRepository;
import util.HtmlBuildUtil;
import util.LoginUtil;

import java.sql.SQLException;
import java.util.Objects;

public class QnaShowView implements View {
    private static QnaShowView qnaShowView = null;

    public static QnaShowView getInstance() {
        if (Objects.isNull(qnaShowView)) {
            synchronized (QnaShowView.class) {
                qnaShowView = new QnaShowView();
            }
            ;
        }
        return qnaShowView;
    }

    @Override
    public byte[] render(HttpRequest httpRequest) {
        int id = Integer.parseInt(httpRequest.getParams().get("id"));
        Qna qna = new QnaRepository().findOneById(id, DBConnector.connect());
        String html = HtmlBuildUtil.buildQnaShow(qna);
        try {
            User user = LoginUtil.checkSession(httpRequest);
            return HtmlBuildUtil.withoutLoginWithUserName(html, "/qna/show", user);
        }catch (NullPointerException e){
            return html.getBytes();
        }
    }
}
