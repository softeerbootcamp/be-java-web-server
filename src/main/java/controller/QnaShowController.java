package controller;

import db.QnARepository;
import request.HttpRequest;
import response.HttpResponse;
import view.Model;

import java.util.Map;

public class QnaShowController implements Controller{

    private static QnaShowController instance;

    private final QnARepository qnARepository;

    public static QnaShowController getInstance(){
        if(instance == null){
            synchronized (QnaShowController.class) {
                instance = new QnaShowController();
            }
        }
        return instance;
    }

    public QnaShowController(){
        qnARepository = QnARepository.getInstance();
    }

    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) {
        Map<String,String> Details = qnARepository.selectOne(request.getQueryByKey("id"));
        model.addAttribute("details",Details);
        return "./templates/qna/show.html";
    }


}
