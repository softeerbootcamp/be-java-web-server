package webserver.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static void readMap(Map<String, String> map){
        for (String key: map.keySet()){
            logger.info(key+ " = " + map.get(key));
        }
    }

    public static String[] parseStringToList(String requestedPath, String delimeter){
        return requestedPath.split(delimeter);
    }

    public static Map<String, String> parseStringToMap(String req, String delimenter){
        Map<String, String> map = new HashMap<>();
        Arrays.stream(req.split(delimenter)).forEach(item->{
            String[] parsedHeader = parseStringToList(item, ":");
            map.put(parsedHeader[0], parsedHeader[1]);
        });
        return map;
    }

    public static String mapToStringSplitWithNewLine(Map<String, String> map){
        StringBuilder sb = new StringBuilder();
        for(String key : map.keySet()){
            sb.append(key + ": " + map.get(key) + "\r\n");
        }
        return sb.toString();
    }

    public static Map<String, String> parseValues(String values, String separator) {
        if (Strings.isNullOrEmpty(values)) {
            return Maps.newHashMap();
        }

        String[] tokens = values.split(separator);

        return Arrays.stream(tokens).map(t -> getKeyValue(t, "=")).filter(Objects::nonNull)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
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
