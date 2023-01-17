package controller;

import service.SignUpService;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class FrontController extends BaseController {
    private static final Map<String,Controller> controllers;
    private static final String SIGN_UP_PATH_URL = "/user/create";

    //객체 캐싱
    static{
        controllers = new HashMap<>();
        SignUpService signUpService = new SignUpService();
        SignUpController signUpController = new SignUpController(signUpService);
        controllers.put(SIGN_UP_PATH_URL,signUpController);
    }

    public Controller getControllerByUrl(String pathUrl){
        return controllers.get(pathUrl);
    }

    /**
     * 프론트 컨트롤러 : 요청 url에 따라 해당되는 컨트롤러를 찾고 해당 컨트롤러의 service를 실행
     * @param request
     * @param response
     */
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        try{
            String url = request.getUrl();
            //일단 디폴트 컨트롤러  - 일반 파일 요청시
            Controller controller = new ReturnFileController();
            //만약 파일 요청이 아니라면
            if(!url.contains(".")) controller = getControllerByUrl(url);
            controller.service(request,response);
        }catch(NullPointerException e){
            System.out.println("요청에 해당되는 컨트롤러가 없습니다. ");
        }
    }
}
