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

import static webserver.utils.CommonUtils.parseValues;

public class HttpRequestUtils {

        private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);
        public static Request parseHttpRequest(InputStream in) throws IOException {

        String requestLine = "";
        String header = "";
        String body = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        //Store Http Request Request Line into HashMap
        requestLine = br.readLine();
        logger.info(requestLine);  //print out http request line

        //Store Http Request header
        StringBuilder sb = new StringBuilder();
        String tempLine = br.readLine();
        while(!tempLine.equals("")){
            sb.append(tempLine).append('\n');
            logger.info(tempLine);  //print out http header
            tempLine = br.readLine();
        }
        header = sb.toString();

        //Store Http Request body
        sb.setLength(0);
        while(br.ready()){
            int next= br.read();
            sb.append((char)next);
        }
        body = sb.toString();

        return Request.of(requestLine, header, body);  //make a Request instance using static factory method
    }

    public static Map<String, String> parseQueryString(String queryString) {
        return parseValues(queryString, "&");
    }


}
