package reader.requestReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.RequestDataType;
import request.Url;

import java.util.HashMap;

public class RequestGetReader implements RequestReader {
    private final String QUERY_STRING_REGEX = "\\?";
    private final String QUERY_STRING_DATAS_REGEX = "&";
    private final String DATA_REGEX = "=";
    public final static String PATH_VARIABLE_KEY = "pathVariableKey";

    private static final Logger logger = LoggerFactory.getLogger(RequestGetReader.class);


    @Override
    public HashMap<String, String> readData(HttpRequest httpRequest) {
        switch (httpRequest.getUrl().getRequestDataType()) {
            case QUERY_STRING:
                return readQueryString(httpRequest);
            case PATH_VARIABLE:
                return readPathVariable(httpRequest.getUrl());
            default:
                return null;
        }

    }

    //TODO 테스트 필요
    private HashMap<String, String> readPathVariable(Url url) {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] parts = url.getUrl().split("/");
        hashMap.put(PATH_VARIABLE_KEY, parts[parts.length - 1]);
        return hashMap;
    }


    private HashMap<String, String> readQueryString(HttpRequest httpRequest) {
        HashMap<String, String> hashMap = new HashMap<>();

        String queryString = httpRequest.getUrl().getUrl().split(QUERY_STRING_REGEX)[1];
        String[] datas = queryString.split(QUERY_STRING_DATAS_REGEX);
        for (String data : datas) {
            String[] mapData = data.split(DATA_REGEX);
            hashMap.put(mapData[0], mapData[1]);
        }
        return hashMap;
    }


}
