package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import controller.ServletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Header;
import request.HttpRequest;
import response.HttpResponse;
import servlet.Servlet;
import util.FileIoUtils;
import util.PathUtils;


public class RequestHandler implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    private ServletController controller;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        controller = new ServletController();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = generateHttpRequest(in);
            HttpResponse httpResponse = controlRequestAndResponse(httpRequest);
            respondToHttpRequest(out, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private HttpRequest generateHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        return HttpRequest.of(br);
    }

    private HttpResponse controlRequestAndResponse(HttpRequest httpRequest)
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
            Class<? extends Servlet> servletClass = controller.getServlet(path);

            logger.debug("servlet name : {}", servletClass.getName());
            Servlet servlet = controller.newInstance(servletClass);
            servlet.service(httpRequest);

            Map<String, String > headerFields = new HashMap<>();
            headerFields.put("Location", "/index.html");
            Header header = new Header(headerFields);
            return HttpResponse.of("302", header);
        }
        throw new AssertionError("HttpRequest는 정적 혹은 동적 컨텐츠 요청만 가능합니다.");
    }

    private void respondToHttpRequest(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);
        httpResponse.respond(dos);
    }

}
