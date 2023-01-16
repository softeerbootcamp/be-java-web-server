package controller;

import db.Database;
import model.User;
import service.SignUpService;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class SignUpController extends BaseController{
    private static final String SIGN_UP_PATH_URL = "/user/create";
    // service는 BaseController에서 더 건들이지 않아도 되므로 오버라이딩 하지 않음

    private static final String REDIRECT_URL ="/index.html";

    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        signUpService.signUp(request,response);
        redirect(REDIRECT_URL,response);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        signUpService.singUpByPost(request,response);
        redirect(REDIRECT_URL,response);
    }

    public void redirect(String redirectUrl,HttpResponse response ){
        response.redirect(redirectUrl);
    }

    public String getPathUrl() {
        return this.SIGN_UP_PATH_URL;
    }
}
