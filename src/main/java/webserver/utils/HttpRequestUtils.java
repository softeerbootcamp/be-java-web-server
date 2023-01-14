package webserver.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.request.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestUtils {

        private static final Logger logger = LoggerFactory.getLogger(HttpResponseWriter.class);
        public static Request parseHttpRequest(InputStream in) throws IOException {

        String requestLine = "";
        String header = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        //Store Http Request Request Line into HashMap
        requestLine = br.readLine();
        logger.info(requestLine);  //print out http request line

        //Store Http Request header into HashMap
        String line = br.readLine();
        while(!line.equals("")){
            header += line + '\n';
            logger.info(line);  //print out http header
            line = br.readLine();
        }

        Request request = Request.of(requestLine, header);  //make a Request instance using static factory method
        return request;
    }

    public static Map<String, String> parseQueryString(String queryString) {
        return parseValues(queryString, "&");
    }


    private static Map<String, String> parseValues(String values, String separator) {
        if (Strings.isNullOrEmpty(values)) {
            return Maps.newHashMap();
        }

        String[] tokens = values.split(separator);

        return Arrays.stream(tokens).map(t -> getKeyValue(t, "=")).filter(p -> p != null)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

    private static Pair getKeyValue(String keyValue, String regex) {
        if (Strings.isNullOrEmpty(keyValue)) {
            return null;
        }

        String[] tokens = keyValue.split(regex);
        if (tokens.length != 2) {
            return null;
        }

        return new Pair(tokens[0], tokens[1]);
    }

    public static class Pair {
        String key;
        String value;

        Pair(String key, String value) {
            this.key = key.trim();
            this.value = value.trim();
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

}
