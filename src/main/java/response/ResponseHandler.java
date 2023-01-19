package response;

import controller.ServletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import servlet.Servlet;
import util.FileIoUtils;
import util.PathUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    /*TODO
    *   Exception 을 이렇게 주렁주렁 작성하는 것이 맞는지
    * */
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
    private static final String lineSeparator = System.lineSeparator();
    private static ResponseHeader responseHeader;

    public static HttpResponse controlRequestAndResponse(HttpRequest httpRequest)
            throws IOException,
            URISyntaxException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {

        if (httpRequest.isForStaticContent()) {
            String path = httpRequest.getPath();
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            Map<String, String > headerFields = new HashMap<>();
            String mData = PathUtils.pathEndCheck(path);

            headerFields.put("Content-Type", mData);
            headerFields.put("Content-Length", String.valueOf(body.length));

            ResponseHeader responseHeader = new ResponseHeader(headerFields);

            return HttpResponse.of("200", responseHeader, body);
        }

        if (httpRequest.isQueryContent()) {
            logger.debug(httpRequest.getPath());

            String path = httpRequest.getPath();
            logger.debug("[ResponseHandler] path : {}",path);
            ServletController servletController = ServletController.of(path);
            Servlet servlet = servletController.newInstance();
            StatusCode status = servlet.service(httpRequest);
            Map<String, String > headerFields = new HashMap<>();

            if(status == StatusCode.Found){ // 회원가입 성공시
                logger.debug("[ResponseHandler] Found");
                headerFields.put("Location", "/index.html");
                ResponseHeader responseHeader = new ResponseHeader(headerFields);
                return HttpResponse.of("302", responseHeader);
            }
            if(status == StatusCode.CustomLogin){ // 로그인 성공시
                logger.debug("[ResponseHandler] CREATE");
                headerFields.put("Location", "/index.html");
                headerFields.put("Set-Cookie", String.format("%s=%s; Path=%s", "sid", "123456", "/"));
                ResponseHeader responseHeader = new ResponseHeader(headerFields);
                return HttpResponse.of("304", responseHeader);
            }
            if(status == StatusCode.SeeOther){ // 잘못된 회원가입
                logger.debug("[ResponseHandler] SeeOther");
                headerFields.put("Location", "/user/form.html");
                ResponseHeader responseHeader = new ResponseHeader(headerFields);
                return HttpResponse.of("303", responseHeader);
            }
            if(status == StatusCode.TemporaryRedirect){ // 잘못된 로그인
                logger.debug("[ResponseHandler] TemporaryRedirect");
                headerFields.put("Location", "/user/login_failed.html");
                headerFields.put("Set-Cookie", String.format("%s=%s", "logined", "false"));
                ResponseHeader responseHeader = new ResponseHeader(headerFields);
                return HttpResponse.of("307", responseHeader);
            }
        }

        throw new RuntimeException("[ERROR] : HttpRequest는 정적 혹은 동적 컨텐츠 요청만 가능합니다.");
    }

}
