package util.global;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Map;

class ConfigReaderTest {

    @Test
    @DisplayName(".yml파일 읽어오기 테스트")
    void getInstance() throws FileNotFoundException {
        //given
        ConfigReader configReader = ConfigReader.getInstance();
        //when
        Map<String, String> config = configReader.getConfig();
        //then
        Assertions.assertThat(config.get(ConfigReader.KEY_DB_URL)).isEqualTo("jdbc:mysql://localhost:3306/web_server_db");
        Assertions.assertThat(config.get(ConfigReader.KEY_DB_USERNAME)).isEqualTo("root");
        Assertions.assertThat(config.get(ConfigReader.KEY_DB_PASSWORD)).isEqualTo("root1234");
    }
}