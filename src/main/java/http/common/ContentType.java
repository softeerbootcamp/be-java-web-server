package http.common;

import java.util.Arrays;
import java.util.Optional;

public enum ContentType {
    TEXT_PLAIN("text/plain", "text"),
    TEXT_HTML("text/html", "html"),
    TEXT_CSS("text/css", "css"),
    IMAGE_JPEG("image/jpeg", "jpeg"),
    IMAGE_PNG("image/png", "png"),
    AUDIO_MPEG("audio/mpeg", "mpeg"),
    AUDIO_OGG("audio.ogg", "ogg"),
    VIDEO_MP4("video/mp4", "mp4"),
    FAVICON("image/avif,image/webp,image/apng,image/svg+xml", "ico"),;

    private final String type;
    private final String extension;

    ContentType(String type, String extension) {
        this.type = type;
        this.extension = extension;
    }

    public String getType() {
        return this.type;
    }

    public static Optional<ContentType> fromExtension(String extension) {
        return Arrays.stream(ContentType.values())
                .filter(type -> type.extension.equals(extension))
                .findFirst();
    }

}