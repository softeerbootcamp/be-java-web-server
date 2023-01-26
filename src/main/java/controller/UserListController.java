package controller;

import db.Database;
import request.HttpRequest;
import response.HttpResponse;
import view.Model;

public class UserListController implements Controller{

    public static final String USER_LIST_URL = "/user/list";

    private static UserListController instance;

    public static UserListController getInstance(){
        if(instance == null){
            synchronized (UserListController.class) {
                instance = new UserListController();
            }
        }
        return instance;
    }
    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) {
        model.addAttribute("users",Database.findAll());
        return "./templates/user/list.html";
    }
}
