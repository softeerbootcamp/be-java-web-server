package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private final String path;
    private final Status status;
    private final String redirect;

    private Response(String path, Status status, String redirect) {
        this.path = path;
        this.status = status;
        this.redirect = redirect;
    }
    
}
