package reader;

import db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import util.UrlType;

import java.util.HashMap;

public class RequestGetReader implements RequestReader{
    private final String QUERY_STRING_REGEX = "\\?";
    private final String QUERY_STRING_DATAS_REGEX = "&";
    private final String DATA_REGEX = "=";
    private static final Logger logger = LoggerFactory.getLogger(RequestGetReader.class);



    @Override
    public byte[] readData(HttpRequest httpRequest) {
        if (httpRequest.getUrl().getUrlType().equals(UrlType.QUERY_STRING)) {
            String queryString = httpRequest.getUrl().getUrl().split(QUERY_STRING_REGEX)[1];
            String[] datas = queryString.split(QUERY_STRING_DATAS_REGEX);

            HashMap<String, String> hashMap = new HashMap<>();

            for (String data : datas) {
                String[] mapData = data.split(DATA_REGEX);
                hashMap.put(mapData[0], mapData[1]);
            }

        }
        return new byte[0];

    }




}
