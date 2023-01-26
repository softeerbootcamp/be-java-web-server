package db;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ConfigReader Test")
public class ConfigReaderTest {

    String configPath = "src/test/resources/db_config.xml";

    @Test
    @DisplayName("read - 모든 설정 필드가 존재하는 경우")
    void read() {
        // given
        // when
        Map<String, String> config = ConfigReader.read(configPath);

        // then
        assertAll(
                () -> assertThat(config.get("host")).isEqualTo("127.0.0.1"),
                () -> assertThat(config.get("port")).isEqualTo("3306"),
                () -> assertThat(config.get("database")).isEqualTo("test"),
                () -> assertThat(config.get("user")).isEqualTo("root"),
                () -> assertThat(config.get("password")).isEqualTo("1234")
        );
    }
}
