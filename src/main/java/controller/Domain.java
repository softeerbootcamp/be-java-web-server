package controller;

import java.util.Arrays;

public enum Domain {
    USER("/user"),
    MAIN("/");

    private String name;

    Domain(String name) {
        this.name = name;
    }

    public static Domain find(String url) {
        return Arrays.stream(Domain.values())
                .filter(domain -> url.startsWith(domain.name))
                .findFirst()
                .orElse(MAIN);
    }
}
