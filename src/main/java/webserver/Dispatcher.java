package webserver;

import controller.*;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Dispatcher {
    public static void dispatch(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = ControllerMapper.mapController(httpRequest);
        controller.service(httpRequest, httpResponse);
    }
}
