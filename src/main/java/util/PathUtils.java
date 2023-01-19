package util;

import webserver.ContentType;
import webserver.ContentTypeMapper;

public class PathUtils {
    public static String pathEndCheck(String path) {
//        if (path.endsWith(ContentTypeMapper.HTML.getExtensionValue())) {
//            return ContentTypeMapper.HTML.getMimeData();
//        }
//        if (path.endsWith(ContentTypeMapper.CSS.getExtensionValue())) {
//            return ContentTypeMapper.CSS.getMimeData();
//        }
//        if (path.endsWith(ContentTypeMapper.JS.getExtensionValue())) {
//            return ContentTypeMapper.JS.getMimeData();
//        }
//        if (path.endsWith(ContentTypeMapper.FONT.getExtensionValue())) {
//            return ContentTypeMapper.FONT.getMimeData();
//        }
//
        return "text/plain";
    }
}
