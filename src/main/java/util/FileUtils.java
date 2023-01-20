package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static byte[] loadFile(String fileName) throws IOException {
        try {
            String viewPath = ViewResolver.resolveViewName(fileName);
            return Files.readAllBytes(new File(viewPath).toPath());
        } catch (IOException e) {
            throw new IOException("해당 경로에 파일이 존재하지 않습니다.");
        }
    }
}
