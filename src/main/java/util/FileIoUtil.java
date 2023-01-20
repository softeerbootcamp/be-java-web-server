package util;

import http.request.HttpUri;
import http.request.ResourceType;

import java.io.File;

public class FileIoUtil {

    public static File getFile(HttpUri httpUri) {
        ResourceType resourceType = httpUri.parseResourceType().get();
        return new File(FileIoUtil.class.getClassLoader().getResource(resourceType.getPath()).getPath() + httpUri.getPath());
    }

    public static File getFile(ResourceType resourceType, String path) {
        return new File(FileIoUtil.class.getClassLoader().getResource(resourceType.getPath()).getPath() + path);
    }

}
