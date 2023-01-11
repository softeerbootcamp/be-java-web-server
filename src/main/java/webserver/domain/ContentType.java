package webserver.domain;

import java.util.Arrays;

public enum ContentType {

    TEXT_HTML("text/html", "html"),
    TEXT_CSS("text/css", "css"),
    TEXT_JAVASCRIPT("text/javascript", "js"),
    TEXT_PLAIN("text/plain", "plain"),
    TEXT_XML("text/xml", "xml"),
    APPLICATION("Application/json", "json"),
    APPLICATION_JAVASCRIPT("Applcation/javascript", "js");

    public String type;
    public String ext;
    private ContentType(String type, String ext){
        this.type= type;
        this.ext = ext;
    }

    public String getType(){
        return type;
    }

    public String getExt(){
        return ext;
    }

    public static ContentType findExtension(String ext){
        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.getExt().equals(ext))
                .findFirst()
                .orElse(TEXT_PLAIN);
    }


}
