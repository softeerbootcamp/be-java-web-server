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

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        //Store Http Request Request Line into HashMap
        requestLine = br.readLine();
        logger.info(requestLine);  //print out http request line

        //Store Http Request header
        String headerLine = br.readLine();
        while(!headerLine.equals("")){
            header += headerLine + '\n';
            logger.info(headerLine);  //print out http header
            headerLine = br.readLine();
        }

        br.readLine(); // to leave out a one-line spacing between http header and body

        //Store Http Request body
        String bodyLine = br.readLine();

        return Request.of(requestLine, headerLine, bodyLine);  //make a Request instance using static factory method
    }

    public static Map<String, String> parseQueryString(String queryString) {
        return parseValues(queryString, "&");
    }


}
