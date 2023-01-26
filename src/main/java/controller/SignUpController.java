package controller;

import service.SignUpService;
import view.Model;
import request.HttpRequest;
import response.HttpResponse;

import java.io.UnsupportedEncodingException;

public class SignUpController implements Controller{
    private static final String SIGN_UP_PATH_URL = "/user/create";
    // service는 BaseController에서 더 건들이지 않아도 되므로 오버라이딩 하지 않음

    private static final String REDIRECT_URL ="/index.html";

    private static SignUpController instance;
    private final SignUpService signUpService;

    public SignUpController() {
        this.signUpService = SignUpService.getInstance();
    }


    public static SignUpController getInstance(){
        if(instance == null){
            synchronized (SignUpController.class){
                instance = new SignUpController();
            }
        }

        return instance;
    }
    @Override
    public String doGet(HttpRequest request, HttpResponse response, Model model) {
        try{
            signUpService.signUp(request);
        }catch(UnsupportedEncodingException e){
            System.out.println(e.getMessage());
        }
        response.redirect(REDIRECT_URL);

        return "";
    }

    @Override
    public String doPost(HttpRequest request, HttpResponse response,Model model) {
        try{
            signUpService.singUpByPost(request);
        }catch(UnsupportedEncodingException e){
            System.out.println(e.getMessage());
        }
        response.redirect(REDIRECT_URL);

        return "";
    }

    public String getPathUrl() {
        return this.SIGN_UP_PATH_URL;
    }
}
