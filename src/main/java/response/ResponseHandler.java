package response;

import controller.ServletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.RequestHeader;
import servlet.Servlet;
import util.FileIoUtils;
import util.PathUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    /*TODO
    *   Exception 을 이렇게 주렁주렁 작성하는 것이 맞는지
    * */
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String lineSeparator = System.lineSeparator();

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

            logger.debug("[ Response Handler ] Content-Type : {}", headerFields.get("Content-Type"));
            logger.debug("[ Response Handler ] Response_body : {}", String.valueOf(body.length));

            String mData = PathUtils.pathEndCheck(path);

            headerFields.put("Content-Type", mData);
            headerFields.put("Content-Length", String.valueOf(body.length));
            ResponseHeader responseHeader = new ResponseHeader(headerFields);

            return HttpResponse.of("200", responseHeader, body);
        }

        if (httpRequest.isQueryContent()) {
            logger.debug("[inQueryMethod]");
            logger.debug(httpRequest.getPath());

            String path = httpRequest.getPath();
            logger.debug("[ResponseHandler] path : {}",path);
            ServletController servletController = ServletController.of(path);
            Servlet servlet = servletController.newInstance();
            StatusLine status = servlet.service(httpRequest);
            Map<String, String > headerFields = new HashMap<>();

            if(status == StatusLine.Found){
                logger.debug("[ResponseHandler] Found");
                headerFields.put("Location", "/index.html");
                ResponseHeader responseHeader = new ResponseHeader(headerFields);
                return HttpResponse.of("302", responseHeader);
            }
            if(status == StatusLine.NotJoin){
                logger.debug("[ResponseHandler] NotJoin");
                headerFields.put("Location", "/user/form.html");
                ResponseHeader responseHeader = new ResponseHeader(headerFields);
                return HttpResponse.of("303", responseHeader);
            }
            if(status == StatusLine.BadRequest){
                logger.debug("[ResponseHandler] NotFound");
                headerFields.put("Location", "/user/login_failed.html");
                ResponseHeader responseHeader = new ResponseHeader(headerFields);
                return HttpResponse.of("401", responseHeader);
            }
        }

        throw new RuntimeException("[ERROR] : HttpRequest는 정적 혹은 동적 컨텐츠 요청만 가능합니다.");
    }

}
