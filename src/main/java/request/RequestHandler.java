package request;

import response.HttpResponseStatus;
import response.Response;
import service.SessionService;
import service.UserService;

import java.util.StringTokenizer;

public interface RequestHandler {
    UserService userService = UserService.getInstance();

    SessionService sessionService = SessionService.getInstance();

    default Response doGet(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    default Response doPost(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    default Response doPut(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    default Response doDelete(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    // TODO: 괴상한 코드 수정
    default byte[] generateDynamicHeader(String cookie, byte[] file) throws IllegalArgumentException{
        String fileString = new String(file);
        if(fileString.contains("${userId}")) {
            fileString = fileString.replace("${userId}", sessionService.isValid(cookie)
                    ? sessionService.findSession(cookie).get().getUser().getUserId() : "");
        }
        if(fileString.contains("로그인")) {
            fileString = fileString.replace("로그인", sessionService.isValid(cookie)
                    ? "" : "로그인");
        }
        if(fileString.contains("회원가입")) {
            fileString = fileString.replace("회원가입", sessionService.isValid(cookie)
                    ? "" : "회원가입");
        }

        return fileString.getBytes();
    }
}
