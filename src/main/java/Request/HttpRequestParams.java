package Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtil;
import util.HttpRequestUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestParams {
    private Map<String, String> params = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParams.class);
    public HttpRequestParams(Map<String, String> params) {
        this.params = params;
    }
    public static HttpRequestParams from(String path) {
        String query = FileIoUtil.checkCreateUser(path);
        if(Objects.nonNull(query)){
            Map<String, String> params = HttpRequestUtil.extractParams(query);
            return new HttpRequestParams(params);
        }
        return null;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
