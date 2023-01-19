package utils;

import enums.ContentType;

public class ExtensionUtils {

    private static final String MESSAGE_UNSUPPORTED_EXTENSION = "지원되지 않는 확장자 입니다.";
    private static final String EXTENSION_DELIMITER = "\\.";

    public static ContentType extractExtension(String url) {
        String[] tokens = url.split(EXTENSION_DELIMITER);
        return ContentType.fromExtension(tokens[tokens.length - 1])
                .orElseThrow(() -> new IllegalArgumentException(MESSAGE_UNSUPPORTED_EXTENSION));
    }
}

