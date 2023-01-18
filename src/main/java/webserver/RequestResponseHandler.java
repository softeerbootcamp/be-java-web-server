package webserver;

import java.io.*;
import java.net.Socket;

import controller.Controller;
import controller.ControllerSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import response.NewResponse;
import response.Response;
import response.ResponseSender;

public class RequestResponseHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);

    private Socket connection;

    public RequestResponseHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            //Response response = new Response(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            Request request = new Request(br);
            // todo : 만약 쿠키가 들어오면 룩업해줘야하는데, 요구사항에 아직 없다. 조금 있다가 추가하자.
            if(request.isRequestHaveCookie()){
                //lookup();
            }
            Controller controller = ControllerSelector.setController(request);
            logger.debug("request cookie : "+request.getRequestHeader().getHeaderValueByKey("Cookie"));
            NewResponse newResponse = controller.controllerService(request);
            ResponseSender responseSender = new ResponseSender(out);
            responseSender.send(newResponse);


        } catch (IOException e) {
            logger.error(e.getMessage());
        }catch (NullPointerException nullPointerException){
            logger.error("아무런 요청이 없습니다!!~!");
        }
    }


}
