import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import utils.StatusCode;

class FileIoUtilsTest {

    @Test
    void testLoadFile_validFile() {
        // Arrange
        String file = "index.html";

        // Act
        byte[] result = FileIoUtils.loadFile(file);

        // Assert
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void testLoadFile_invalidFile() {
        // Arrange
        String file = "not_existing_file.html";

        // Act
        byte[] result = FileIoUtils.loadFile(file);

        // Assert
        Assertions.assertThat(result).isEqualTo(StatusCode.NOTFOUND.toString().getBytes());
    }
}
