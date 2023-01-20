package webserver;

import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.util.List;
import java.util.Map;

public class ArgumentResolver {

    public static Map<String, String> checkParameters(Map<String, String> queryStrs, List<String> paramList) {
        paramList.stream()
                .filter(param->queryStrs.get(param) == null)
                .findFirst()
                .ifPresent( a ->{throw new HttpRequestException(StatusCodes.BAD_REQUEST, "<script>alert('입력값을 확인해주세요'); history.go(-1);</script>");});
        return queryStrs;
    }

}
