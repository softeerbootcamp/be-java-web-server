package controller;

import filesystem.Extension;

import java.util.Arrays;

public enum Domain {
    USER("/user"),
    POST("/post"),
    MAIN("/");

    private String name;

    Domain(String name) {
        this.name = name;
    }

    public static Domain find(String url) {
        if (Extension.isStaticResource(url)) {
            return MAIN;
        }
        return Arrays.stream(Domain.values())
                .filter(domain -> url.startsWith(domain.name))
                .findFirst()
                .orElse(MAIN);
    }

    public String getName() {
        return name;
    }
}
