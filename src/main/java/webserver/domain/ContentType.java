package webserver.domain;

import java.util.Arrays;

public enum ContentType {

    TEXT_HTML("text/html; charset= utf-8", "html"),
    TEXT_CSS("text/css; charset= utf-8", "css"),
    TEXT_JAVASCRIPT("text/javascript; charset= utf-8", "js"),
    TEXT_PLAIN("text/plain; charset= utf-8", "plain"),
    TEXT_XML("text/xml; charset= utf-8", "xml"),
    APPLICATION("Application/json; charset= utf-8", "json"),
    APPLICATION_JAVASCRIPT("Applcation/javascript; charset= utf-8", "js");

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
