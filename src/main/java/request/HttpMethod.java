package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtil;

import java.io.BufferedReader;
import java.util.HashMap;

public enum HttpMethod {
    GET {
        @Override
        public HttpRequestParams getParamsByMethod(BufferedReader br, int length, HttpRequestStartLine startLine) {
            switch (startLine.getType()) {
                case QUERY_STRING:
                    return HttpRequestParams.from(FileIoUtil.getQueryString(startLine.getPath()));
                case PATH_VARIABLE:
                    return HttpRequestParams.from(FileIoUtil.getPathVariable(startLine.getPath()));
            }
            return new HttpRequestParams(new HashMap<>());
        }

    },
    POST {
        @Override
        public HttpRequestParams getParamsByMethod(BufferedReader br, int length, HttpRequestStartLine startLine) {
            String data = FileIoUtil.readBuffer(br, length);
            return HttpRequestParams.from(data);
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(HttpMethod.class);

    public abstract HttpRequestParams getParamsByMethod(BufferedReader br, int length, HttpRequestStartLine startLine);

}
