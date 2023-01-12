package controller;

import java.util.Arrays;

public enum DomainList {
    USER("/user"),
    MAIN("/");

    private String name;

    DomainList(String name) {
        this.name = name;
    }

    public static DomainList find(String url) {
        return Arrays.stream(DomainList.values())
                .filter(domain -> url.startsWith(domain.name))
                .findFirst()
                .orElse(MAIN);
    }
}
