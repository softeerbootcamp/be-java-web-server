package util;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class FileIoUtilTest {
    private static Logger logger = LoggerFactory.getLogger(FileIoUtilTest.class);
    @Test
    public void mappingPathTest(){
        Path result = FileIoUtil.mappingPath("/css/styles.css");
        Path path = new File("./src/main/resources/static/css/styles.css").toPath();
        //logger.debug("result: "+path.toString());
        assertThat(result).isEqualTo(path);
    }
}
