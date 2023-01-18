package reader.requestReader;

import reader.requestReader.RequestReader;
import request.HttpRequest;

import java.util.HashMap;

public class RequestPostReader implements RequestReader {

    private final String REGEX_FORM = "&";
    private final String REGEX_FORM_DATA = "=";


    @Override
    public HashMap<String, String> readData(HttpRequest httpRequest) {
        StringBuilder data = httpRequest.getRequestBody().getData();
        String[] keyValuePairs = data.toString().split(REGEX_FORM);
        HashMap<String, String> map = new HashMap<>();
        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split(REGEX_FORM_DATA);
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }
}
