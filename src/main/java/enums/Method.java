package enums;

import java.util.Arrays;

public enum Method {
    GET("GET");

    private String name;

    Method(String name) {
        this.name = name;
    }

    public static Method find(String chunk) {
        return Arrays.stream(Method.values())
                .filter(method -> method.name.equals(chunk))
                .findFirst()
                .get();
    }
}
