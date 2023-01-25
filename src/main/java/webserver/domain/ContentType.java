package webserver.domain;

import java.util.Arrays;

public enum ContentType {

    TEXT_HTML("text/html; " + Constants.charsetUTF8, "html"),
    TEXT_CSS("text/css; " + Constants.charsetUTF8, "css"),
    TEXT_JAVASCRIPT("text/javascript; " + Constants.charsetUTF8, "js"),
    TEXT_PLAIN("text/plain; " + Constants.charsetUTF8, "plain"),
    TEXT_XML("text/xml; " + Constants.charsetUTF8, "xml"),
    FORM_URLENCODED("application/x-www-form-urlencoded" + Constants.charsetUTF8, "form"),
    APPLICATION("Application/json; " + Constants.charsetUTF8, "json"),
    APPLICATION_JAVASCRIPT("Applcation/javascript; " + Constants.charsetUTF8, "js");

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

    public static ContentType findByType(String name){
        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.getType().startsWith(name))
                .findFirst()
                .orElse(FORM_URLENCODED);
    }
    private static class Constants { // static member class
        private static final String charsetUTF8 = "charset= utf-8";
    }



}
