package request;

import controller.Controller;
import controller.ServletContainer;
import controller.StaticController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestMapper {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestMapper.class);

    private final ServletContainer servletContainer;
    private final StaticController staticController;

    public HttpRequestMapper(ServletContainer servletContainer, StaticController staticController) {
        this.servletContainer = servletContainer;
        this.staticController = staticController;
    }

    public Controller getController(HttpRequest httpRequest) {
        if (servletContainer.hasMappingServlet(httpRequest)) {
            return servletContainer.getController(httpRequest);
        }

        if (staticController.isForStaticContent(httpRequest)) {
            return staticController;
        }
        throw new RuntimeException(httpRequest.getDefaultPath());
    }
}
