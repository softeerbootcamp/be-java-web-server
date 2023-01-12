package utils;


import java.io.File;
import java.nio.file.Files;
import java.util.Set;

public class FileIoUtils {
    private static final String STATIC_RESOURCE_PATH = FileIoUtils.class.getClassLoader().getResource("./static").getPath();
    private static final String TEMPLATE_RESOURCE_PATH = FileIoUtils.class.getClassLoader().getResource("./templates").getPath();
    private static final Set<String> TEMPLATE_EXTENSIONS = Set.of("html", "ico");

    public static byte[] loadFile(String file) {
        try {
            String fileExtension = getExtension(file);
            if (TEMPLATE_EXTENSIONS.contains(fileExtension))
                return Files.readAllBytes(new File(TEMPLATE_RESOURCE_PATH + file).toPath());
            return Files.readAllBytes(new File(STATIC_RESOURCE_PATH + file).toPath());
        } catch (Exception e) {
            throw new IllegalArgumentException("해당하는 파일이 존재하지 않습니다.");
        }
    }

    public static String getExtension(String file){
        return file.substring(file.lastIndexOf(".") + 1);
    }
}
