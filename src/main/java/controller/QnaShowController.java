package controller;

import db.DBConnection;
import request.HttpRequest;
import response.HttpResponse;
import view.Model;

public class QnaShowController implements Controller{

    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) {
        model.addAttribute("id",request.getQueryByKey("id"));
        return "./templates/qna/show.html";
    }


}
