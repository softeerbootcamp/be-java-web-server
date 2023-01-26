package controller;

import db.DBConnection;
import request.HttpRequest;
import response.HttpResponse;
import view.Model;

public class QnaShowController implements Controller{

    private static QnaShowController instance;

    public static QnaShowController getInstance(){
        if(instance == null){
            synchronized (QnaShowController.class) {
                instance = new QnaShowController();
            }
        }
        return instance;
    }

    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) {
        model.addAttribute("id",request.getQueryByKey("id"));
        return "./templates/qna/show.html";
    }


}
