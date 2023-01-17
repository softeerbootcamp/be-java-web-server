package util;

import request.Url;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FileType {
    HTML(".html","text/html"),
    CSS(".css","text/css"),
    JS(".js","text/js"),
    FAVICON(".ico","image/x-icon"),
    WOFF_FONT (".woff","application/font-woff"),
    TFF_FONT (".tff","application/x-font-ttf"),
    ;

    private String extension;
    private String type;

    FileType(String extension,String type) {
        this.extension = extension;
        this.type = type;
    }

    public static FileType getFileType(Url url) {
        return Arrays.stream(values())
                .filter(value -> url.getUrl().contains(value.extension))
                .findAny().orElse(null);
    }

    public static List<String> getAllExtension() {
        return Arrays.stream(values())
                .map(value -> value.extension)
                .collect(Collectors.toList());
    }

    public String getExtension() {
        return extension;
    }

    public String getType() {
        return type;
    }
}
