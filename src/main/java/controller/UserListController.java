package controller;

import db.Database;
import model.User;
import utils.FileIoUtils;
import view.Model;
import webserver.*;
import java.util.Collection;

public class UserListController implements Controller{

    public static final String USER_LIST_URL = "/user/list";
    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) {
        model.addAttribute("users",Database.findAll());
        return "./templates/user/list.html";
    }
}
