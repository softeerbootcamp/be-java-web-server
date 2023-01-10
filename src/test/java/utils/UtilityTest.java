package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    @Test
    void stringSplit() {
        String line = "GET /index.html HTTP/1.1";
        String[] token = line.split(" ");
        String indexString = token[1].trim();
        assertThat(indexString).isEqualTo("/index.html");
    }
}