package request.method.POST.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;

import java.util.HashMap;
import java.util.Map;

public class POSTUserRegisterHandler implements POSTHandler{
    private static final Logger logger = LoggerFactory.getLogger(POSTUserRegisterHandler.class);

    private final static POSTUserRegisterHandler POST_USER_REGISTER_HANDLER;

    static {
        POST_USER_REGISTER_HANDLER = new POSTUserRegisterHandler();
    }

    public static POSTHandler getInstance() {
        return POST_USER_REGISTER_HANDLER;
    }

    @Override
    public byte[] handle(Request request) {
        Map<String, String> requestBody = parseBody(request);
        logger.debug("{}", requestBody);
        return new byte[0];
    }

    private Map<String, String> parseBody(Request request) {
        Map<String, String> map = new HashMap<>();
        String[] tokens = request.getRequestBody().split("&");
        for(String token : tokens) {
            String[] subTokens = token.split("=");
            map.put(subTokens[0], subTokens.length == 2 ? subTokens[1] : "");
        }
        return map;
    }
}
