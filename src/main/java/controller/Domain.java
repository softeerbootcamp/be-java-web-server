package controller;

import filesystem.Extension;

import java.util.Arrays;

public enum Domain {
    USER("/user"),
    MAIN("/");

    private String name;

    Domain(String name) {
        this.name = name;
    }

    public static Domain find(String url) {
        if (isStaticResourceRequest(url)) {
            return MAIN;
        }
        return Arrays.stream(Domain.values())
                .filter(domain -> url.startsWith(domain.name))
                .findFirst()
                .orElse(MAIN);
    }

    private static boolean isStaticResourceRequest(String url) {
        return Extension.isStaticResource(url);
    }

    public String getName() {
        return name;
    }
}
