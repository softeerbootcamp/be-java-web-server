package webserver;

import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.util.List;
import java.util.Map;

public class ArgumentResolver {

    public static Map<String, String> checkParameters(Map<String, String> queryStrs, List<String> paramList) {
        for(String key : queryStrs.keySet()){
            System.out.println(queryStrs.get(key));
            if(!paramList.contains(key))
                throw new HttpRequestException(StatusCodes.BAD_REQUEST);
        }
        return queryStrs;
    }

}
