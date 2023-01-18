package controller;

import service.SignUpService;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class FrontController implements Controller {
    private static final Map<String,Controller> controllers;
    private static final String SIGN_UP_PATH_URL = "/user/create";
    private static final String LOGIN_PATH_URL ="/user/login";

    //객체 캐싱
    static{
        controllers = new HashMap<>();
        SignUpService signUpService = new SignUpService();
        SignUpController signUpController = new SignUpController(signUpService);
        LoginController loginInController = new LoginController();
        controllers.put(SIGN_UP_PATH_URL,signUpController);
        controllers.put(LOGIN_PATH_URL,loginInController);
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
            //Controller controller = new ReturnFileController();
            Controller controller = new NotFoundExceptionHandler();
            //일단 디폴트 컨트롤러  - 일반 파일 요청시
            if(url.contains(".")) controller = new ReturnFileController();
            //만약 파일 요청이 아니라면
            if(!url.contains(".")) controller = getControllerByUrl(url);
            controller.service(request,response);
        }catch(NullPointerException e){
            NotFoundExceptionHandler.showErrorPage(response);
            System.out.println("해당 url에 대한 응답이 없습니다");
        } catch (FileNotFoundException e) {
            NotFoundExceptionHandler.showErrorPage(response);
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
