package util;

import java.util.Arrays;

public enum FileType {
    HTML(".html","html"),
    CSS(".css","css"),
    JS(".js","js"),
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
                .findAny().get();
    }

    public String getExtension() {
        return extension;
    }

    public String getType() {
        return type;
    }
}
