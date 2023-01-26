package controller;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.ResponseFactory;
import session.HttpCookie;
import session.HttpSessions;
import webserver.RequestResponseHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class TemplateController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    @Override
    public ResponseFactory controllerService(Request request) throws IOException, SQLException {
        logger.debug("firstLine : " + request.getRequestLine().getURL());
        String url = request.getRequestLine().getURL();
        ControllerTypeEnum controllerTypeEnum = ControllerTypeEnum.TEMPLATE;
        HttpCookie cookie = request.getHttpCookie();

        String addedLine = null;
        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + url).toPath());
        body = DynamicRenderer.dynamicIndex_ViewAllBoard(body);
        // 로그인 할 경우 Dynamic renderer
        if (cookie.isLogin()) {
            body = DynamicRenderer.dynamicIndex_LoginBtnToUserBtn(body, request.getRequestHeader().getHeaderValueByKey("Cookie").split("=")[1]);
            body = DynamicRenderer.dynamicIndex_LogoutBtnOn(body);
            body = DynamicRenderer.dynamicIndex_SignUpBtnOff(body);
        }
        // 로그아웃 시 작동으로 변경해야함.
        if (!cookie.isLogin()) {
            body = DynamicRenderer.dynamicIndex_LogoutBtnOff(body);
        }
        // 사용자 리스트 출력일 경우
        if (url.contains("list.html")) {
            body = DynamicRenderer.dynamicListHtml(body);
        }
        // 로그인이 되지 않았는데 질문하기 접근 할 경우
        if (!cookie.isLogin() && url.contains("/qna/form.html")) {
            controllerTypeEnum = ControllerTypeEnum.REDIRECT;
            addedLine = "Location : /index.html";
        }
        ResponseFactory responseFactory = new ResponseFactory.Builder()
                .setResponseStatusLine(controllerTypeEnum)
                .setResponseHeader(ContentTypeEnum.HTML, body.length)
                .addResponseHeader(addedLine)
                .setResponseBody(body)
                .build();
        return responseFactory;
    }
}
