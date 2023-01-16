package http.common;

import http.common.HeaderAttribute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Header {

    private static final String HEADER_SEPARATOR = "\n\r";
    private static final String ATTRIBUTE_SEPARATOR = ":";
    private static final String EMPTY_STRING = "";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> attributes = new HashMap<>();

    public Header() {
    }

    public Header(String message) {
        this.attributes = parseMessage(message);
    }

    private Map<String, String> parseMessage(String message) {
        Map<String, String> att = new HashMap<>();
        Arrays.stream(message.split(HEADER_SEPARATOR))
                .forEach(attribute -> {
                    List<String> splitPair = getSplitPair(attribute);
                    att.put(splitPair.get(KEY_INDEX), splitPair.get(VALUE_INDEX));
                });
        return att;
    }

    private List<String> getSplitPair(String attribute) {
        List<String> split = Arrays.stream(attribute.split(ATTRIBUTE_SEPARATOR)).map(String::trim).collect(Collectors.toList());
        if (split.size() > VALUE_INDEX) {
            return List.of(split.get(KEY_INDEX), split.get(VALUE_INDEX));
        }
        return List.of(split.get(KEY_INDEX), EMPTY_STRING);
    }

    public void setAttribute(HeaderAttribute key, String value) {
        attributes.put(key.getValue(), value);
    }

    public void setAttribute(HeaderAttribute key, Integer value) {
        setAttribute(key, Integer.toString(value));
    }

    public String getAttribute(HeaderAttribute key) {
        String value = attributes.get(key.getValue());
        if (value == null) {
            return key.getDefaultValue();
        }
        return value;
    }

    @Override
    public String toString() {
        return String.join(HEADER_SEPARATOR, attributes.keySet().stream()
                .map(k -> k + ATTRIBUTE_SEPARATOR + attributes.get(k))
                .collect(Collectors.toList())) + HEADER_SEPARATOR + HEADER_SEPARATOR;
    }
}
