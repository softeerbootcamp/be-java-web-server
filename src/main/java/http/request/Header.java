package http.request;

import http.response.enums.ResponseAttribute;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Header {
    private Map<String, String> attributes = new HashMap<>();

    public Header() {
    }

    public Header(String message) {
        this.attributes = parseMessage(message);
    }

    private Map<String, String> parseMessage(String message) {
        Map<String, String> att = new HashMap<>();
        String[] lines = message.split("\n\r");
        for (String line : lines) {
            String[] chunks = line.split(":");
            att.put(chunks[0].trim(), chunks[1].trim());
        }
        return att;
    }

    public void setAttribute(ResponseAttribute key, String value) {
        attributes.put(key.getValue(), value);
    }

    public void setAttribute(ResponseAttribute key, Integer value) {
        setAttribute(key, Integer.toString(value));
    }

    public String getAttribute(ResponseAttribute key) {
        return attributes.get(key.getValue());
    }

    @Override
    public String toString() {
        return String.join("\n\r", attributes.keySet().stream().map(k -> k + ":" + attributes.get(k)).collect(Collectors.toList())) + "\n\r" + "\n\r";
    }
}
