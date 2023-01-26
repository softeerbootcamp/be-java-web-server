package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtil;

import java.io.BufferedReader;

public enum HttpMethod {
    GET {
        @Override
        public HttpRequestParams getParamsByMethod(BufferedReader br, int length, HttpRequestStartLine startLine){
            String query = FileIoUtil.splitQuery(startLine.getPath());
            return HttpRequestParams.from(query);
        }

    },
    POST {
        @Override
        public HttpRequestParams getParamsByMethod(BufferedReader br, int length, HttpRequestStartLine startLine){
            String data = FileIoUtil.readBuffer(br, length);
            return HttpRequestParams.from(data);
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(HttpMethod.class);

    public abstract HttpRequestParams getParamsByMethod(BufferedReader br, int length, HttpRequestStartLine startLine);

}
