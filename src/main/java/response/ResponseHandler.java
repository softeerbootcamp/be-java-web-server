package response;

import controller.ServletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Header;
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
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

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

            logger.debug("Content-Type : {}", headerFields.get("Content-Type"));
            headerFields.put("Content-Length", String.valueOf(body.length));
            Header header = new Header(headerFields);
            return HttpResponse.of("200", header, body);
        }

        if (httpRequest.isQueryContent()) {
            String path = httpRequest.getPath();
            ServletController servletController = ServletController.of(path);
            Servlet servlet = servletController.newInstance();
            servlet.service(httpRequest);

            Map<String, String > headerFields = new HashMap<>();

            headerFields.put("Location", "/index.html");
            Header header = new Header(headerFields);
            return HttpResponse.of("302", header);
        }

        throw new AssertionError("HttpRequest는 정적 혹은 동적 컨텐츠 요청만 가능합니다.");
    }

}
