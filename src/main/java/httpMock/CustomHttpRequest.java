package httpMock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CustomHttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(CustomHttpRequest.class);
    private String httpMethod;
    private String protocolVersion;
    private String url;
    private final Map<String, String> requestParams;
    private final Map<String, List<String>> requestHeaders;

    public CustomHttpRequest(InputStream inputStream) {
        requestHeaders = new HashMap<>();
        setFirstLineHeaders(inputStream);
        requestParams = setRequestParams();
        logger.info("url : " + this.url + ", method : " + this.httpMethod + ", params : " + this.requestParams);
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    private void addToRequestHeader(String line) {
        String[] split1 = line.split(":");
        String itemName = split1[0].trim();
        if (split1.length < 2) {
            return;
        }
        String[] itemString = Arrays.stream(split1[1].split(",")).map(String::trim).toArray(String[]::new);
        List<String> itemList = new ArrayList<>(List.of(itemString));
        this.requestHeaders.put(itemName, itemList);
    }

    private Map<String, String> setRequestParams() {
        Map<String, String> requestParams = new HashMap<>();
        if (url.contains("?")) {
            String tempParamStr = url.substring(url.indexOf("?") + 1);
            String[] params = tempParamStr.split("&");
            for (String param : params) {
                try {
                    String[] data = param.split("=");
                    requestParams.put(data[0], data[1]);
                } catch (Exception e) {
                    logger.error("String split failed : " + param);
                }
            }
        }
        return requestParams;
    }

    private void setFirstLineHeaders(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        try {
            String[] firstLine = firstLineSpliter(br.readLine());
            this.httpMethod = firstLine[0];
            this.url = java.net.URLDecoder.decode(firstLine[1], StandardCharsets.UTF_8);
            this.protocolVersion = firstLine[2];
            while (br.ready()) {
                addToRequestHeader(br.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] firstLineSpliter(String firstLine) {
        String[] result = firstLine.split("\\s");
        if (result.length != 3) {
            throw new IllegalArgumentException("First Line of Http must have 2 space : " + firstLine);
        }
        return result;
    }
}
