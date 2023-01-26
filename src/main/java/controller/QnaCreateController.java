package controller;

import request.HttpRequest;
import db.DBConnector;
import model.Qna;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import db.QnaRepository;
import response.HttpResponse;

import java.util.Objects;

public class QnaCreateController implements AuthController{
    public static final String PATH = "/qna/create";
    private static QnaCreateController qnaCreateController = null;
    private final Logger logger = LoggerFactory.getLogger(QnaCreateController.class);

    public static QnaCreateController getInstance() {
        if(Objects.isNull(qnaCreateController)){
            synchronized (QnaCreateController.class){
                qnaCreateController = new QnaCreateController();
            }
        }
        return qnaCreateController;
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        logger.debug("select Qna Controller");
        Qna qna = Qna.createQna(httpRequest);
        QnaRepository.getInstance().saveQna(qna, DBConnector.connect());
        return redirect("/index", httpRequest);
    }
}
