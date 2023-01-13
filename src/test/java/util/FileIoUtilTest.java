package util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class FileIoUtilTest {
    private static Logger logger = LoggerFactory.getLogger(FileIoUtilTest.class);
    @Test
    public void mappingPathTest(){
        Path result = FileIoUtil.mappingDirectoryPath("/css/styles.css");
        Path path = new File("./src/main/resources/static/css/styles.css").toPath();
        //logger.debug("result: "+path.toString());
        assertThat(result).isEqualTo(path);
    }
    @Test
    public void findExtenstionTest(){
        String extension = FileIoUtil.findExtension("/");
        logger.debug("extension: "+ extension);
    }
}
