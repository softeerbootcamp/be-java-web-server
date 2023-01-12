package response;

import java.util.Arrays;

public enum ContentType {
	HTML("html", "text/html"),
	CSS("css", "text/css"),
	JS("js", "text/javascript"),
	JPEG("jpeg", "image/jpeg"),
	PNG("png", "image/png"),
	FAVICON("ico", "image/ico"),
	TTF("ttf", "font/ttf"),
	WOFF("woff", "font/woff");

	private final String extension;

	private final String contentType;

	ContentType(String extension, String contentType) {
		this.extension = extension;
		this.contentType = contentType;
	}

	public static ContentType findContentType(String extension) {
		return Arrays.stream(ContentType.values())
			.filter(e -> e.extension.equals(extension))
			.findFirst()
			.orElseThrow(() -> new RuntimeException(extension + "과 일치하는 ContentType이 없습니다."));
	}

	public String getContentType() {
		return contentType;
	}
}
